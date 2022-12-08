package com.kodlamaio.filterservice.kafka;

import com.kodlamaio.common.events.inventories.InventoryCreatedEvent;
import com.kodlamaio.common.events.inventories.brands.BrandDeletedEvent;
import com.kodlamaio.common.events.inventories.brands.BrandUpdatedEvent;
import com.kodlamaio.common.events.inventories.cars.CarDeletedEvent;
import com.kodlamaio.common.events.inventories.cars.CarUpdatedEvent;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalCreatedEvent;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalDeletedEvent;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalUpdatedEvent;
import com.kodlamaio.common.events.inventories.models.ModelDeletedEvent;
import com.kodlamaio.common.events.inventories.models.ModelUpdatedEvent;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.entities.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryConsumer.class);
    private final FilterService service;
    private final ModelMapperService mapper;

    public InventoryConsumer(FilterService service, ModelMapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(
            topics = "inventory-car-created"
            , groupId = "inventory-create"
    )
    public void consume(InventoryCreatedEvent event) {
        Filter filter = mapper.forRequest().map(event, Filter.class);
        service.save(filter);
        LOGGER.info("Inventory created event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-car-deleted"
            , groupId = "car-delete"
    )
    public void consume(CarDeletedEvent event) {
        service.delete(event.getCarId());
        LOGGER.info("Car deleted event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-car-updated"
            , groupId = "car-update"
    )
    public void consume(CarUpdatedEvent event) {
        Filter filter = mapper.forRequest().map(event, Filter.class);
        String id = service.getByCarId(event.getCarId()).getId();
        filter.setId(id);
        service.save(filter);
        LOGGER.info("Car updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-brand-deleted"
            , groupId = "brand-delete"
    )
    public void consume(BrandDeletedEvent event) {
        service.deleteAllByBrandId(event.getBrandId());

        LOGGER.info("Brand deleted event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-brand-updated"
            , groupId = "brand-update"
    )
    public void consume(BrandUpdatedEvent event) {
        service.getByBrandId(event.getId()).forEach(filter -> {
            filter.setBrandName(event.getName());
            service.save(filter);
        });

        LOGGER.info("Brand updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-model-deleted"
            , groupId = "model-delete"
    )
    public void consume(ModelDeletedEvent event) {
        service.deleteAllByModelId(event.getModelId());

        LOGGER.info("Model deleted event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-model-updated"
            , groupId = "model-update"
    )
    public void consume(ModelUpdatedEvent event) {
        service.getByModelId(event.getId()).forEach(filter -> {
            filter.setModelName(event.getName());
            filter.setBrandId(event.getBrandId());
            filter.setBrandName(service.getByBrandId(event.getBrandId()).get(0).getBrandName());
            service.save(filter);
        });

        LOGGER.info("Model updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-rental-created"
            , groupId = "car-rental-create"
    )
    public void consume(CarRentalCreatedEvent event) {
        Filter filter = service.getByCarId(event.getCarId());
        filter.setState(3); // 3 = Rented
        service.save(filter);

        LOGGER.info("Car rental created event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-rental-updated"
            , groupId = "car-rental-update"
    )
    public void consume(CarRentalUpdatedEvent event) {
        Filter oldCar = service.getByCarId(event.getOldCarId());
        Filter newCar = service.getByCarId(event.getNewCarId());
        oldCar.setState(1); // 1 = Available
        newCar.setState(3); // 3 = Rented
        service.save(oldCar);
        service.save(newCar);

        LOGGER.info("Car rental updated event consumed: {}", event);
    }

    @KafkaListener(
            topics = "inventory-rental-deleted"
            , groupId = "car-rental-delete"
    )
    public void consume(CarRentalDeletedEvent event) {
        Filter car = service.getByCarId(event.getCarId());
        car.setState(1); // 1 = Available
        service.save(car);
        LOGGER.info("Car rental deleted event consumed: {}", event);
    }
}
