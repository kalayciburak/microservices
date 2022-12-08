package com.kodlamaio.rentalservice.business.dto.requests.create;

import com.kodlamaio.common.dto.CreatePaymentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {
    @NotNull
    private String carId;
    @NotNull
    @Min(value = 1)
    private int rentedForDays;
    @NotNull
    @Min(value = 0)
    private double dailyPrice;
    private CreatePaymentRequestDTO paymentRequest;
}
