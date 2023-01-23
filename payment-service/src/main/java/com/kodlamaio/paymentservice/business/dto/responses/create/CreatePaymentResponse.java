package com.kodlamaio.paymentservice.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponse {
    private String id;
    private String customerId;
    private String cardNumber;
    private String cardholder;
    private int cardExpirationYear;
    private int cardExpirationMonth;
    private String cardCvv;
    private double balance;
    private String customerUserName;
    private String customerFirstName;
    private String customerLastName;
    private String customerEmail;
}
