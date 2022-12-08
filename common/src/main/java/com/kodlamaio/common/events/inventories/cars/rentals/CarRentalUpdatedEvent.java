package com.kodlamaio.common.events.inventories.cars.rentals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalUpdatedEvent {
    private String message;
    private String newCarId;
    private String oldCarId;
}
