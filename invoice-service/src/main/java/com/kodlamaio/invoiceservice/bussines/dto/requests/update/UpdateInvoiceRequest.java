package com.kodlamaio.invoiceservice.bussines.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInvoiceRequest {
    @NotBlank
    private String carId;
    @NotBlank
    private String cardholder;
    @NotBlank
    private String modelName;
    @NotBlank
    private String brandName;
    @NotNull
    @Min(1886)
    private int modelYear;
    @NotNull
    @Min(0)
    private double dailyPrice;
    @NotNull
    @Min(0)
    private double totalPrice;
    @NotNull
    @Min(0)
    private int rentedForDays;
}
