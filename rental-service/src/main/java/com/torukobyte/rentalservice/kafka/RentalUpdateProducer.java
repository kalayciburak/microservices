package com.torukobyte.rentalservice.kafka;

import com.torukobyte.common.events.RentalUpdatedEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class RentalUpdateProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalUpdateProducer.class);

    private final NewTopic topic;

    private final KafkaTemplate<String, RentalUpdateProducer> kafkaTemplate;

    public RentalUpdateProducer(NewTopic topic, KafkaTemplate<String, RentalUpdateProducer> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RentalUpdatedEvent rentalUpdatedEvent) {
        LOGGER.info(String.format("Rental updated event => %s", rentalUpdatedEvent.toString()));

        Message<RentalUpdatedEvent> message = MessageBuilder
                .withPayload(rentalUpdatedEvent)
                .setHeader(KafkaHeaders.TOPIC, topic.name()).build();

        kafkaTemplate.send(message);
    }
}
