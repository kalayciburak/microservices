package com.kodlamaio.inventoryservice.repository;

import com.kodlamaio.inventoryservice.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, String> {
    boolean existsByNameIgnoreCase(String name);
}
