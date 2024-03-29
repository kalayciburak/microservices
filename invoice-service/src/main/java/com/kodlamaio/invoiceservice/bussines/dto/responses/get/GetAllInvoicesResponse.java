package com.kodlamaio.invoiceservice.bussines.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllInvoicesResponse {
    private String id;
    private String carId;
    private String customerId;
    private String cardholder;
    private String modelName;
    private String brandName;
    private int modelYear;
    private double dailyPrice;
    private double totalPrice;
    private int rentedForDays;
    private String customerUserName;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
}