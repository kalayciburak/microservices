package com.torukobyte.inventoryservice.repository;

import com.torukobyte.inventoryservice.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, String> {
    boolean existsByNameIgnoreCase(String name);
}
