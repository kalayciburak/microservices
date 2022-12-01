package com.torukobyte.rentalservice.business.concretes;

import com.torukobyte.common.util.exceptions.BusinessException;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.rentalservice.business.abstracts.RentalService;
import com.torukobyte.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.torukobyte.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.torukobyte.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import com.torukobyte.rentalservice.entities.Rental;
import com.torukobyte.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private RentalRepository repository;
    private ModelMapperService mapper;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        List<Rental> rentals = repository.findAll();
        List<GetAllRentalsResponse> data = rentals.stream()
                                                  .map(rental -> mapper.forResponse()
                                                                       .map(rental,
                                                                            GetAllRentalsResponse.class))
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
        Rental rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(UUID.randomUUID().toString());
        repository.save(rental);
        CreateRentalResponse data = mapper.forResponse().map(rental, CreateRentalResponse.class);

        return data;
    }

    @Override
    public UpdateRentalResponse update(UpdateRentalRequest request, String id) {
        checkIfRentalExists(id);
        Rental rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(id);
        repository.save(rental);
        UpdateRentalResponse data = mapper.forResponse().map(rental, UpdateRentalResponse.class);

        return data;
    }

    @Override
    public void delete(String id) {
        checkIfRentalExists(id);
        repository.deleteById(id);
    }

    private void checkIfRentalExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("Rental not found");
        }
    }
}
