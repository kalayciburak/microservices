package com.kodlamaio.rentalservice.kafka;

import com.kodlamaio.common.constants.Events;
import com.kodlamaio.common.events.payments.PaymentReceivedEvent;
import com.kodlamaio.common.events.rentals.RentalCreatedEvent;
import com.kodlamaio.common.events.rentals.RentalDeletedEvent;
import com.kodlamaio.common.events.rentals.RentalUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RentalProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public RentalProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RentalCreatedEvent rentalCreatedEvent) {
        LOGGER.info(String.format(Events.Logs.Producer.Rental.Created, rentalCreatedEvent.toString()));

        Message<RentalCreatedEvent> message = MessageBuilder
                .withPayload(rentalCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Rental.Created).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(RentalUpdatedEvent rentalUpdatedEvent) {
        LOGGER.info(String.format(Events.Logs.Producer.Rental.Updated, rentalUpdatedEvent.toString()));

        Message<RentalUpdatedEvent> message = MessageBuilder
                .withPayload(rentalUpdatedEvent)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Rental.Updated).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(RentalDeletedEvent rentalDeleteEvent) {
        LOGGER.info(String.format(Events.Logs.Producer.Rental.Deleted, rentalDeleteEvent.toString()));

        Message<RentalDeletedEvent> message = MessageBuilder
                .withPayload(rentalDeleteEvent)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Rental.Deleted).build();

        kafkaTemplate.send(message);
    }

    public void sendMessage(PaymentReceivedEvent event) {
        LOGGER.info(String.format(Events.Logs.Producer.Rental.PaymentReceived, event.toString()));

        Message<PaymentReceivedEvent> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC, Events.Producer.Rental.PaymentReceived).build();

        kafkaTemplate.send(message);
    }
}
