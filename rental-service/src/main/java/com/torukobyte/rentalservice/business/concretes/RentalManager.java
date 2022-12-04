package com.torukobyte.rentalservice.business.concretes;

import com.torukobyte.common.events.payments.PaymentReceivedEvent;
import com.torukobyte.common.events.rentals.RentalCreatedEvent;
import com.torukobyte.common.events.rentals.RentalDeletedEvent;
import com.torukobyte.common.events.rentals.RentalUpdatedEvent;
import com.torukobyte.common.util.exceptions.BusinessException;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.rentalservice.business.abstracts.RentalService;
import com.torukobyte.rentalservice.business.dto.requests.create.CreatePaymentRequest;
import com.torukobyte.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.torukobyte.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.torukobyte.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import com.torukobyte.rentalservice.configuration.client.CarClient;
import com.torukobyte.rentalservice.configuration.client.PaymentClient;
import com.torukobyte.rentalservice.entities.Rental;
import com.torukobyte.rentalservice.kafka.RentalProducer;
import com.torukobyte.rentalservice.repository.RentalRepository;
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
    public CreateRentalResponse add(CreateRentalRequest request, CreatePaymentRequest paymentRequest) {
        carClient.checkIfCarAvailable(request.getCarId());
        Rental rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(UUID.randomUUID().toString());
        rental.setDateStarted(LocalDateTime.now());
        double totalPrice = request.getRentedForDays() * request.getDailyPrice();
        rental.setTotalPrice(totalPrice);
        paymentClient.checkIfPaymentSuccessful(paymentRequest.getCardNumber(),
                                               paymentRequest.getFullName(),
                                               paymentRequest.getCardExpirationYear(),
                                               paymentRequest.getCardExpirationMonth(),
                                               paymentRequest.getCardCvv(),
                                               totalPrice);
        repository.save(rental);
        RentalCreatedEvent rentalCreatedEvent = new RentalCreatedEvent();
        rentalCreatedEvent.setCarId(rental.getCarId());
        rentalCreatedEvent.setMessage("Rental Created");
        rentalProducer.sendMessage(rentalCreatedEvent);
        PaymentReceivedEvent paymentReceivedEvent = new PaymentReceivedEvent();
        paymentReceivedEvent.setCarId(rental.getCarId());
        paymentReceivedEvent.setFullName(paymentRequest.getFullName());
        paymentReceivedEvent.setDailyPrice(request.getDailyPrice());
        paymentReceivedEvent.setTotalPrice(totalPrice);
        paymentReceivedEvent.setRentedForDays(request.getRentedForDays());
        paymentReceivedEvent.setRentedAt(rental.getDateStarted());
        rentalProducer.sendMessage(paymentReceivedEvent);
        CreateRentalResponse data = mapper.forResponse().map(rental, CreateRentalResponse.class);

        return data;
    }

    @Override
    public UpdateRentalResponse update(UpdateRentalRequest request, String id) {
        checkIfRentalExists(id);
        carClient.checkIfCarAvailable(request.getCarId());
        RentalUpdatedEvent rentalUpdatedEvent = new RentalUpdatedEvent();
        Rental rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(id);
        rentalUpdatedEvent.setOldCarId(repository.findById(id).orElseThrow().getCarId());
        repository.save(rental);
        rentalUpdatedEvent.setNewCarId(rental.getCarId());
        rentalUpdatedEvent.setMessage("Rental Updated");
        rentalProducer.sendMessage(rentalUpdatedEvent);
        UpdateRentalResponse data = mapper.forResponse().map(rental, UpdateRentalResponse.class);

        return data;
    }

    @Override
    public void delete(String id) {
        checkIfRentalExists(id);
        RentalDeletedEvent event = new RentalDeletedEvent();
        event.setCarId(repository.findById(id).orElseThrow().getCarId());
        event.setMessage("Rental Deleted");
        rentalProducer.sendMessage(event);
        repository.deleteById(id);
    }

    private void checkIfRentalExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("RENTAL.NOT_FOUND");
        }
    }
}
