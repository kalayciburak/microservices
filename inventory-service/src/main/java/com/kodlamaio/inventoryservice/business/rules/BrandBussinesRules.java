package com.kodlamaio.inventoryservice.business.rules;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.utils.exceptions.BusinessException;
import com.kodlamaio.inventoryservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BrandBussinesRules {
    private final BrandRepository repository;

    public void checkIfBrandExistsById(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Brand.NotExists);
        }
    }

    public void checkIfBrandExistsByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException(Messages.Brand.Exists);
        }
    }
}
