package com.torukobyte.inventoryservice.kafka;

import com.torukobyte.common.events.RentalCreatedEvent;
import com.torukobyte.inventoryservice.business.abstracts.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RentalCreateConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalCreateConsumer.class);
    private final CarService carService;

    public RentalCreateConsumer(CarService carService) {
        this.carService = carService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            , groupId = "rental-create"
    )
    public void consume(RentalCreatedEvent event) {
        carService.changeState(2, event.getCarId());
        LOGGER.info("Car state changed!");
    }
}