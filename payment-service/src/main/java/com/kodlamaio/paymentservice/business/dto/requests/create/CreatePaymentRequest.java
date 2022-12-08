package com.kodlamaio.paymentservice.business.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    @NotBlank(message = "Card number is required")
    @Length(min = 16, max = 16, message = "Card number must be 16 characters long")
    private String cardNumber;
    @NotBlank(message = "Full name is required")
    @Length(min = 3, message = "Full name must be at least 3 characters long")
    private String fullName;
    @NotNull(message = "Card expiration year is required")
    @Min(value = 2022, message = "Card expiration year must be at least current year")
    private int cardExpirationYear;
    @NotNull(message = "Card expiration month is required")
    @Min(value = 1, message = "Card expiration month must be between 1 and 12")
    @Max(value = 12, message = "Card expiration month must be between 1 and 12")
    private int cardExpirationMonth;
    @NotBlank(message = "Card CVV is required")
    @Length(min = 3, max = 3, message = "Card CVV must be 3 characters long")
    private String cardCvv;
    @NotNull(message = "Balance is required")
    @Min(value = 1, message = "Balance must be at least 1")
    private double balance;
}
