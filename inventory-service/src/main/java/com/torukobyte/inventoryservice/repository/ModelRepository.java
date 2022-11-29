package com.torukobyte.inventoryservice.repository;

import com.torukobyte.inventoryservice.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, String> {
    boolean existsByNameIgnoreCase(String name);
}