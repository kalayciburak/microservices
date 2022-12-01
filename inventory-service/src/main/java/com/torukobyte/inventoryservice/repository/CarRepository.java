package com.torukobyte.inventoryservice.repository;

import com.torukobyte.inventoryservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface CarRepository extends JpaRepository<Car, String> {
    boolean existsByPlateIgnoreCase(String plate);
    @Modifying
    @Query(value = "update Cars set state = :state where id = :id", nativeQuery = true)
    @Transactional
    void changeStateByCarId(int state, String id);
}
