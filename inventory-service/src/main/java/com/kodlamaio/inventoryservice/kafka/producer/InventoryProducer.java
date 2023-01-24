package com.kodlamaio.inventoryservice.kafka.producer;

import com.kodlamaio.common.constants.Events;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class InventoryProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InventoryProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(InventoryCreatedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Car.Created, event.toString()));

        Message<InventoryCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Car.Created).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(CarUpdatedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Car.Updated, event.toString()));

        Message<CarUpdatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Car.Updated).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(CarDeletedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Car.Deleted, event.toString()));

        Message<CarDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Car.Deleted).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(BrandUpdatedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Brand.Updated, event.toString()));

        Message<BrandUpdatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Brand.Updated).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(BrandDeletedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Brand.Deleted, event.toString()));

        Message<BrandDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Brand.Deleted).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(ModelUpdatedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Model.Updated, event.toString()));

        Message<ModelUpdatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Model.Updated).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(ModelDeletedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Model.Deleted, event.toString()));

        Message<ModelDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Model.Deleted).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(CarRentalCreatedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Rental.Created, event.toString()));

        Message<CarRentalCreatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Car.RentalCreated).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(CarRentalUpdatedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Rental.Updated, event.toString()));

        Message<CarRentalUpdatedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Car.RentalUpdated).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(CarRentalDeletedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Rental.Deleted, event.toString()));

        Message<CarRentalDeletedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Car.RentalDeleted).build();

        kafkaTemplate.send(message);
    }
}
