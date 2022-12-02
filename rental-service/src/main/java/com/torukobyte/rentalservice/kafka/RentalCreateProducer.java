package com.torukobyte.rentalservice.kafka;

import com.torukobyte.common.events.RentalCreatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RentalCreateProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalCreateProducer.class);

    private final NewTopic topic;

    private final KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate;

    public RentalCreateProducer(NewTopic topic, KafkaTemplate<String, RentalCreatedEvent> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RentalCreatedEvent rentalCreatedEvent) {
        LOGGER.info(String.format("Rental created event => %s", rentalCreatedEvent.toString()));

        Message<RentalCreatedEvent> message = MessageBuilder
                .withPayload(rentalCreatedEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name()).build();

        kafkaTemplate.send(message);
    }
}