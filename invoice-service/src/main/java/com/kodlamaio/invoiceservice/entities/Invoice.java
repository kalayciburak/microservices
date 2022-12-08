package com.kodlamaio.invoiceservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoice")
public class Invoice {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "car_id")
    private String carId;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "model_name")
    private String modelName;
    @Column(name = "brand_name")
    private String brandName;
    @Column(name = "model_year")
    private int modelYear;
    @Column(name = "daily_price")
    private double dailyPrice;
    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "rented_for_days")
    private int rentedForDays;
    @Column(name = "rented_at")
    private LocalDateTime rentedAt;
}