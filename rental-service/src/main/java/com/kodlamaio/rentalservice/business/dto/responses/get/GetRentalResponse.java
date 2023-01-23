package com.kodlamaio.rentalservice.business.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetRentalResponse {
    private String id;
    private String carId;
    private String customerId;
    private LocalDateTime dateStarted;
    private int rentedForDays;
    private double dailyPrice;
    private double totalPrice;
    private String customerUserName;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
}
