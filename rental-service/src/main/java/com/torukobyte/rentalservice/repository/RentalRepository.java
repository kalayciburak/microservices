package com.torukobyte.rentalservice.repository;

import com.torukobyte.rentalservice.entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, String> {
}
