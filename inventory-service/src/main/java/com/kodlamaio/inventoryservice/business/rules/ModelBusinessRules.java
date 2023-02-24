package com.kodlamaio.inventoryservice.business.rules;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.utils.exceptions.BusinessException;
import com.kodlamaio.inventoryservice.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModelBusinessRules {
    private final ModelRepository repository;

    public void checkIfExistsById(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Model.NotExists);
        }
    }

    public void checkIfExistsByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException(Messages.Model.Exists);
        }
    }
}
