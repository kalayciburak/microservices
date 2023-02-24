package com.kodlamaio.rentalservice.business.rules;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.utils.exceptions.BusinessException;
import com.kodlamaio.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RentalBusinessRules {
    private final RentalRepository repository;

    public void checkIfRentalExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Rental.NotFound);
        }
    }
}
