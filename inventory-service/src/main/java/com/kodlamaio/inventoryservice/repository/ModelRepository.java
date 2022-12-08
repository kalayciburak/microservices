package com.kodlamaio.inventoryservice.repository;

import com.kodlamaio.inventoryservice.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, String> {
    boolean existsByNameIgnoreCase(String name);
}