package com.kodlamaio.common.events.rentals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalDeletedEvent {
    private String message;
    private String carId;
}
