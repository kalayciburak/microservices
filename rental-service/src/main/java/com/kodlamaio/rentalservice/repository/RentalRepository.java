package com.kodlamaio.rentalservice.repository;

import com.kodlamaio.rentalservice.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, String> {
}
