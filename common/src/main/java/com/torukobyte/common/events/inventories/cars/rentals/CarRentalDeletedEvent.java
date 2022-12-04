package com.torukobyte.common.events.inventories.cars.rentals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalDeletedEvent {
    private String message;
    private String carId;
}