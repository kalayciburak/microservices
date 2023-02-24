package com.kodlamaio.inventoryservice.business.rules;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.utils.exceptions.BusinessException;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryservice.entities.Car;
import com.kodlamaio.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarBusinessRules {
    private final CarRepository repository;

    public void checkIfCarExistsById(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Car.NotExists);
        }
    }

    public void checkCarPlateUniqueness(UpdateCarRequest request, String id) {
        Car car = repository.findById(id).orElseThrow();
        if (!car.getPlate().equals(request.getPlate())) {
            checkIfCarExistsByPlate(request.getPlate());
        }
    }

    public void checkIfCarExistsByPlate(String plate) {
        if (repository.existsByPlateIgnoreCase(plate)) {
            throw new BusinessException(Messages.Car.Exists);
        }
    }

    public void checkCarAvailability(String id) {
        Car car = repository.findById(id).get();
        if (car.getState() != 1) {
            throw new BusinessException(Messages.Car.NotAvailable);
        }
    }
}
