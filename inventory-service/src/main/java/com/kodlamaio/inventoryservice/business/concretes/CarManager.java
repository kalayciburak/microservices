package com.kodlamaio.inventoryservice.business.concretes;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.events.inventories.InventoryCreatedEvent;
import com.kodlamaio.common.events.inventories.cars.CarDeletedEvent;
import com.kodlamaio.common.events.inventories.cars.CarUpdatedEvent;
import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.kodlamaio.inventoryservice.entities.Car;
import com.kodlamaio.inventoryservice.kafka.producer.InventoryProducer;
import com.kodlamaio.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarManager implements CarService {
    private final CarRepository repository;
    private final ModelMapperService mapper;
    private final InventoryProducer producer;

    @Override
    public List<GetAllCarsResponse> getAll() {
        List<Car> cars = repository.findAll();
        List<GetAllCarsResponse> response = cars
                .stream()
                .map(car -> mapper.forResponse().map(car, GetAllCarsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetCarResponse getById(String id) {
        checkIfCarExistsById(id);
        Car car = repository.findById(id).orElseThrow();
        GetCarResponse response = mapper.forResponse().map(car, GetCarResponse.class);

        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        checkIfCarExistsByPlate(request.getPlate());
        Car car = mapper.forRequest().map(request, Car.class);
        car.setId(UUID.randomUUID().toString());
        car.setState(1); // 1 = available , 2 = maintenance, 3 = rented
        repository.save(car);
        CreateCarResponse response = mapper.forResponse().map(car, CreateCarResponse.class);
        addToMongodb(car.getId());

        return response;
    }

    @Override
    public UpdateCarResponse update(UpdateCarRequest request, String id) {
        checkIfCarExistsById(id);
        Car car = mapper.forRequest().map(request, Car.class);
        car.setId(id);
        repository.save(car);
        UpdateCarResponse response = mapper.forResponse().map(car, UpdateCarResponse.class);
        updateMongo(request, id);

        return response;
    }

    @Override
    public void delete(String id) {
        checkIfCarExistsById(id);
        repository.deleteById(id);
        deleteMongo(id);
    }

    @Override
    public void changeState(int state, String id) {
        repository.changeStateByCarId(state, id);
    }

    @Override
    public void checkIfCarAvailable(String id) {
        Car car = repository.findById(id).get();
        if (car.getState() != 1) {
            throw new BusinessException(Messages.Car.NotAvailable);
        }
    }

    private void checkIfCarExistsById(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Car.NotExists);
        }
    }

    private void checkIfCarExistsByPlate(String plate) {
        if (repository.existsByPlateIgnoreCase(plate)) {
            throw new BusinessException(Messages.Car.Exists);
        }
    }

    private void addToMongodb(String id) {
        Car car = repository.findById(id).orElseThrow();
        InventoryCreatedEvent event = mapper.forResponse().map(car, InventoryCreatedEvent.class);
        producer.sendMessage(event);
    }

    private void updateMongo(UpdateCarRequest request, String id) {
        Car car = repository.findById(id).orElseThrow();
        car.getModel().setId(request.getModelId());
        car.getModel().getBrand().setId(car.getModel().getBrand().getId());
        car.setState(request.getState());
        car.setPlate(request.getPlate());
        car.setModelYear(request.getModelYear());

        CarUpdatedEvent event = mapper.forResponse().map(car, CarUpdatedEvent.class);
        producer.sendMessage(event);
    }

    private void deleteMongo(String id) {
        CarDeletedEvent event = new CarDeletedEvent();
        event.setCarId(id);
        producer.sendMessage(event);
    }
}

