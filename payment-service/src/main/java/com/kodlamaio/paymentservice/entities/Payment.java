package com.kodlamaio.paymentservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "card_number")
    private String cardNumber;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "card_expiration_year")
    private int cardExpirationYear;
    @Column(name = "card_expiration_month")
    private int cardExpirationMonth;
    @Column(name = "card_cvv")
    private String cardCvv;
    @Column(name = "balance")
    private double balance;
}
