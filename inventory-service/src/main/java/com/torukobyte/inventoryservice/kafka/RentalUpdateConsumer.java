package com.torukobyte.inventoryservice.kafka;

import com.torukobyte.common.events.RentalUpdatedEvent;
import com.torukobyte.inventoryservice.business.abstracts.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RentalUpdateConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalCreateConsumer.class);
    private final CarService carService;

    public RentalUpdateConsumer(CarService carService) {
        this.carService = carService;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            , groupId = "rental-update")
    public void consume(RentalUpdatedEvent event) {
        carService.changeState(3, event.getOldCarId());
        carService.changeState(2, event.getNewCarId());
        LOGGER.info("Car state updated!");
    }
}
