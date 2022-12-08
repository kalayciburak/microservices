package com.kodlamaio.inventoryservice.kafka.consumer;

import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalCreatedEvent;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalDeletedEvent;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalUpdatedEvent;
import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalDeletedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdatedEvent;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import com.kodlamaio.inventoryservice.kafka.producer.InventoryProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RentalConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);
    private final CarService carService;
    private final InventoryProducer producer;

    public RentalConsumer(CarService carService, InventoryProducer producer) {
        this.carService = carService;
        this.producer = producer;
    }

    @KafkaListener(
            topics = "rental-created"
            , groupId = "rental-create"
    )
    public void consume(RentalCreatedEvent event) {
        carService.changeState(3, event.getCarId());
        CarRentalCreatedEvent carRentalCreatedEvent = new CarRentalCreatedEvent();
        carRentalCreatedEvent.setCarId(event.getCarId());
        carRentalCreatedEvent.setMessage("Car rented!");
        producer.sendMessage(carRentalCreatedEvent);
        LOGGER.info("Car rented!");
    }

    @KafkaListener(
            topics = "rental-updated"
            , groupId = "rental-update")
    public void consume(RentalUpdatedEvent event) {
        carService.changeState(1, event.getOldCarId());
        carService.changeState(3, event.getNewCarId());
        CarRentalUpdatedEvent carRentalUpdatedEvent = new CarRentalUpdatedEvent();
        carRentalUpdatedEvent.setNewCarId(event.getNewCarId());
        carRentalUpdatedEvent.setOldCarId(event.getOldCarId());
        carRentalUpdatedEvent.setMessage("Car rented state updated!");
        producer.sendMessage(carRentalUpdatedEvent);
        LOGGER.info("Car rented state updated!");
    }

    @KafkaListener(
            topics = "rental-deleted"
            , groupId = "rental-delete")
    public void consume(RentalDeletedEvent event) {
        carService.changeState(1, event.getCarId());
        CarRentalDeletedEvent carRentalDeletedEvent = new CarRentalDeletedEvent();
        carRentalDeletedEvent.setCarId(event.getCarId());
        carRentalDeletedEvent.setMessage("Car deleted from rental!");
        producer.sendMessage(carRentalDeletedEvent);
        LOGGER.info("Car deleted from rental!");
    }
}
