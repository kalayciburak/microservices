package com.kodlamaio.inventoryservice.kafka.consumer;

import com.kodlamaio.common.constants.Events;
import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalCreatedEvent;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalDeletedEvent;
import com.kodlamaio.common.events.inventories.cars.rentals.CarRentalUpdatedEvent;
import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalDeletedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdatedEvent;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalConsumer {
    private static Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);

    private final CarService carService;

    @KafkaListener(
            topics = Events.Producer.Rental.Created
            , groupId = Events.Consumer.Rental.CreateGroupId)
    public void consume(RentalCreatedEvent event) {
        carService.changeState(3, event.getCarId());
        CarRentalCreatedEvent carRentalCreatedEvent = new CarRentalCreatedEvent();
        carRentalCreatedEvent.setCarId(event.getCarId());
        carRentalCreatedEvent.setMessage(Messages.Rental.CarRented);
        LOGGER.info(Events.Logs.Consumer.Rental.Created, event);
    }

    @KafkaListener(
            topics = Events.Producer.Rental.Updated
            , groupId = Events.Consumer.Rental.UpdateGroupId)
    public void consume(RentalUpdatedEvent event) {
        carService.changeState(1, event.getOldCarId());
        carService.changeState(3, event.getNewCarId());
        CarRentalUpdatedEvent carRentalUpdatedEvent = new CarRentalUpdatedEvent();
        carRentalUpdatedEvent.setNewCarId(event.getNewCarId());
        carRentalUpdatedEvent.setOldCarId(event.getOldCarId());
        carRentalUpdatedEvent.setMessage(Messages.Rental.CarRentedStateUpdated);
        LOGGER.info(Events.Logs.Consumer.Rental.Updated, event);
    }

    @KafkaListener(
            topics = Events.Producer.Rental.Deleted
            , groupId = Events.Consumer.Rental.DeleteGroupId)
    public void consume(RentalDeletedEvent event) {
        carService.changeState(1, event.getCarId());
        CarRentalDeletedEvent carRentalDeletedEvent = new CarRentalDeletedEvent();
        carRentalDeletedEvent.setCarId(event.getCarId());
        carRentalDeletedEvent.setMessage(Messages.Rental.CarReturned);
        LOGGER.info(Events.Logs.Consumer.Rental.Deleted, event);
    }
}
