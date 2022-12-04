package com.torukobyte.filterservice.api.controllers;

import com.torukobyte.filterservice.business.abstracts.FilterService;
import com.torukobyte.filterservice.entities.Filter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/filters")
public class FiltersController {
    private final FilterService service;

    @GetMapping
    public List<Filter> getAll() {
        return service.getAll();
    }

    @GetMapping("/brand")
    public List<Filter> getByBrandName(@RequestParam String brandName) {
        return service.getByBrandName(brandName);
    }

    @GetMapping("/model")
    public List<Filter> getByModelName(@RequestParam String modelName) {
        return service.getByModelName(modelName);
    }

    @GetMapping("/plate")
    public List<Filter> getByPlate(@RequestParam String plate) {
        return service.getByPlate(plate);
    }
}
