package com.kodlamaio.filterservice.kafka;

import com.kodlamaio.common.constants.Events;
import com.kodlamaio.common.events.inventories.InventoryCreatedEvent;
import com.kodlamaio.common.events.inventories.brands.BrandDeletedEvent;
import com.kodlamaio.common.events.inventories.brands.BrandUpdatedEvent;
import com.kodlamaio.common.events.inventories.cars.CarDeletedEvent;
import com.kodlamaio.common.events.inventories.cars.CarUpdatedEvent;
import com.kodlamaio.common.events.inventories.models.ModelDeletedEvent;
import com.kodlamaio.common.events.inventories.models.ModelUpdatedEvent;
import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalDeletedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdatedEvent;
import com.kodlamaio.common.utils.mapping.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.entities.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@CacheEvict(value = "filters", allEntries = true)
public class InventoryConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryConsumer.class);

    private final FilterService service;
    private final ModelMapperService mapper;

    public InventoryConsumer(FilterService service, ModelMapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(
            topics = Events.Producer.Car.Created
            , groupId = Events.Consumer.Car.CreateGroupId
    )
    public void consume(InventoryCreatedEvent event) {
        Filter filter = mapper.forRequest().map(event, Filter.class);
        service.save(filter);
        LOGGER.info(Events.Logs.Consumer.Car.Created, event);
    }

    @KafkaListener(
            topics = Events.Producer.Car.Deleted
            , groupId = Events.Consumer.Car.DeleteGroupId
    )
    public void consume(CarDeletedEvent event) {
        service.delete(event.getCarId());
        LOGGER.info(Events.Logs.Consumer.Car.Deleted, event);
    }

    @KafkaListener(
            topics = Events.Producer.Car.Updated
            , groupId = Events.Consumer.Car.UpdateGroupId
    )
    public void consume(CarUpdatedEvent event) {
        Filter filter = mapper.forRequest().map(event, Filter.class);
        String id = service.getByCarId(event.getCarId()).getId();
        filter.setId(id);
        service.save(filter);
        LOGGER.info(Events.Logs.Consumer.Car.Updated, event);
    }

    @KafkaListener(
            topics = Events.Producer.Brand.Deleted
            , groupId = Events.Consumer.Brand.UpdateGroupId
    )
    public void consume(BrandDeletedEvent event) {
        service.deleteAllByBrandId(event.getBrandId());

        LOGGER.info(Events.Logs.Consumer.Brand.Deleted, event);
    }

    @KafkaListener(
            topics = Events.Producer.Brand.Updated
            , groupId = Events.Consumer.Brand.UpdateGroupId
    )
    public void consume(BrandUpdatedEvent event) {
        service.getByBrandId(event.getId()).forEach(filter -> {
            filter.setBrandName(event.getName());
            service.save(filter);
        });

        LOGGER.info(Events.Logs.Consumer.Brand.Updated, event);
    }

    @KafkaListener(
            topics = Events.Producer.Model.Deleted
            , groupId = Events.Consumer.Model.DeleteGroupId
    )
    public void consume(ModelDeletedEvent event) {
        service.deleteAllByModelId(event.getModelId());

        LOGGER.info(Events.Logs.Consumer.Model.Deleted, event);
    }

    @KafkaListener(
            topics = Events.Producer.Model.Updated
            , groupId = Events.Consumer.Model.UpdateGroupId
    )
    public void consume(ModelUpdatedEvent event) {
        service.getByModelId(event.getId()).forEach(filter -> {
            filter.setModelName(event.getName());
            filter.setBrandId(event.getBrandId());
            filter.setBrandName(service.getByBrandId(event.getBrandId()).get(0).getBrandName());
            service.save(filter);
        });

        LOGGER.info(Events.Logs.Consumer.Model.Updated, event);
    }

    @KafkaListener(
            topics = Events.Producer.Rental.Created
            , groupId = Events.Consumer.Car.RentalCreateGroupId
    )
    public void consume(RentalCreatedEvent event) {
        Filter filter = service.getByCarId(event.getCarId());
        filter.setState(3); // 3 = Rented
        service.save(filter);

        LOGGER.info(Events.Logs.Consumer.Rental.Created, event);
    }

    @KafkaListener(
            topics = Events.Producer.Rental.Updated
            , groupId = Events.Consumer.Car.RentalUpdateGroupId
    )
    public void consume(RentalUpdatedEvent event) {
        Filter oldCar = service.getByCarId(event.getOldCarId());
        Filter newCar = service.getByCarId(event.getNewCarId());
        oldCar.setState(1); // 1 = Available
        newCar.setState(3); // 3 = Rented
        service.save(oldCar);
        service.save(newCar);

        LOGGER.info(Events.Logs.Consumer.Rental.Updated, event);
    }

    @KafkaListener(
            topics = Events.Producer.Rental.Deleted
            , groupId = Events.Consumer.Car.RentalDeleteGroupId
    )
    public void consume(RentalDeletedEvent event) {
        Filter car = service.getByCarId(event.getCarId());
        car.setState(1); // 1 = Available
        service.save(car);
        LOGGER.info(Events.Logs.Consumer.Rental.Deleted, event);
    }
}
