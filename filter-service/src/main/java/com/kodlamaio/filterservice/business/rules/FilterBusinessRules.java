package com.kodlamaio.filterservice.business.rules;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.filterservice.repository.FilterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FilterBusinessRules {
    private FilterRepository repository;

    public void checkIfExistByPlate(String plate) {
        if (!repository.existsByPlate(plate)) {
            throw new RuntimeException(Messages.Filter.NotExists);
        }
    }
}
