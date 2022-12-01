package com.torukobyte.rentalservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rentals")
public class Rental {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "car_id")
    private String carId;
    @Column(name = "date_started")
    private LocalDateTime dateStarted = LocalDateTime.now();
    @Column(name = "rented_for_days")
    private int rentedForDays;
    @Column(name = "daily_price")
    private double dailyPrice;
    @Column(name = "total_price")
    private double totalPrice;
}
