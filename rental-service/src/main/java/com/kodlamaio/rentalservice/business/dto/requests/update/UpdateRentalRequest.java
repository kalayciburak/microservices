package com.kodlamaio.rentalservice.business.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentalRequest {
    @NotNull
    private String carId;
    @NotNull
    private LocalDateTime dateStarted;
    @NotNull
    @Min(value = 1)
    private int rentedForDays;
    @NotNull
    @Min(0)
    private double dailyPrice;
}