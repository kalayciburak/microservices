package com.kodlamaio.rentalservice.business.concretes;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import com.kodlamaio.common.events.payments.PaymentReceivedEvent;
import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalDeletedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdatedEvent;
import com.kodlamaio.common.utils.exceptions.BusinessException;
import com.kodlamaio.common.utils.mapping.ModelMapperService;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import com.kodlamaio.rentalservice.configuration.client.CarClient;
import com.kodlamaio.rentalservice.configuration.client.PaymentClient;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.kafka.RentalProducer;
import com.kodlamaio.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private RentalRepository repository;
    private ModelMapperService mapper;
    private RentalProducer rentalProducer;
    private CarClient carClient;
    private PaymentClient paymentClient;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        List<Rental> rentals = repository.findAll();
        List<GetAllRentalsResponse> data = rentals
                .stream()
                .map(rental -> mapper.forResponse().map(rental, GetAllRentalsResponse.class))
                .toList();

        return data;
    }

    @Override
    public GetRentalResponse getById(String id) {
        checkIfRentalExists(id);
        Rental rental = repository.findById(id).orElseThrow();
        GetRentalResponse data = mapper.forResponse().map(rental, GetRentalResponse.class);

        return data;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        carClient.checkIfCarAvailable(request.getCarId());
        Rental rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(UUID.randomUUID().toString());
        rental.setDateStarted(LocalDateTime.now());
        double totalPrice = request.getRentedForDays() * request.getDailyPrice();
        rental.setTotalPrice(totalPrice);
        CreateRentalPaymentRequest paymentRequest = new CreateRentalPaymentRequest();
        mapper.forRequest().map(request.getPaymentRequest(), paymentRequest);
        paymentRequest.setPrice(totalPrice);

        paymentClient.checkIfPaymentSuccessful(paymentRequest);
        repository.save(rental);
        rentalCreatedEvent(rental);
        paymentReceivedEvent(request, rental, totalPrice);
        CreateRentalResponse response = mapper.forResponse().map(rental, CreateRentalResponse.class);

        return response;
    }

    @Override
    public UpdateRentalResponse update(UpdateRentalRequest request, String id) {
        checkIfRentalExists(id);
        carClient.checkIfCarAvailable(request.getCarId());
        Rental rental = mapper.forRequest().map(request, Rental.class);
        rentalUpdatedEvent(id, rental);
        UpdateRentalResponse response = mapper.forResponse().map(rental, UpdateRentalResponse.class);

        return response;
    }

    @Override
    public void delete(String id) {
        checkIfRentalExists(id);
        rentalDeletedEvent(id);
        repository.deleteById(id);
    }

    private void checkIfRentalExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Rental.NotFound);
        }
    }

    private void rentalCreatedEvent(Rental rental) {
        RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
        rentalCreatedEvent.setCarId(rental.getCarId());
        rentalCreatedEvent.setMessage(Messages.Rental.Created);
        rentalProducer.sendMessage(rentalCreatedEvent);
    }

    private void rentalUpdatedEvent(String id, Rental rental) {
        RentalUpdatedEvent rentalUpdatedEvent = new RentalUpdatedEvent();
        rental.setId(id);
        rentalUpdatedEvent.setOldCarId(repository.findById(id).orElseThrow().getCarId());
        repository.save(rental);
        rentalUpdatedEvent.setNewCarId(rental.getCarId());
        rentalUpdatedEvent.setMessage(Messages.Rental.Updated);
        rentalProducer.sendMessage(rentalUpdatedEvent);
    }

    private void rentalDeletedEvent(String id) {
        RentalDeletedEvent event = new RentalDeletedEvent();
        event.setCarId(repository.findById(id).orElseThrow().getCarId());
        event.setMessage(Messages.Rental.Deleted);
        rentalProducer.sendMessage(event);
    }

    private void paymentReceivedEvent(CreateRentalRequest request, Rental rental, double totalPrice) {
        PaymentReceivedEvent paymentReceivedEvent = new PaymentReceivedEvent();
        paymentReceivedEvent.setCarId(rental.getCarId());
        paymentReceivedEvent.setFullName(request.getPaymentRequest().getFullName());
        paymentReceivedEvent.setDailyPrice(request.getDailyPrice());
        paymentReceivedEvent.setTotalPrice(totalPrice);
        paymentReceivedEvent.setRentedForDays(request.getRentedForDays());
        paymentReceivedEvent.setRentedAt(rental.getDateStarted());
        paymentReceivedEvent.setBrandName(carClient.getCarResponse(rental.getCarId()).getBrandName());
        paymentReceivedEvent.setModelName(carClient.getCarResponse(rental.getCarId()).getModelName());
        paymentReceivedEvent.setModelYear(carClient.getCarResponse(rental.getCarId()).getModelYear());
        rentalProducer.sendMessage(paymentReceivedEvent);
    }
}
