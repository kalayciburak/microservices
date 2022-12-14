package com.kodlamaio.filterservice.api.controllers;

import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.responses.GetFilterResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/filters")
public class FiltersController {
    private final FilterService service;

    @GetMapping
    public List<GetAllFiltersResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/brand")
    public List<GetAllFiltersResponse> getByBrandName(@RequestParam String brandName) {
        return service.getByBrandName(brandName);
    }

    @GetMapping("/model")
    public List<GetAllFiltersResponse> getByModelName(@RequestParam String modelName) {
        return service.getByModelName(modelName);
    }

    @GetMapping("/plate")
    public GetFilterResponse getByPlate(@RequestParam String plate) {
        return service.getByPlate(plate);
    }

    @GetMapping("/plate-search")
    public List<GetAllFiltersResponse> searchByPlate(@RequestParam String plate) {
        return service.searchByPlate(plate);
    }

    @GetMapping("/brand-search")
    public List<GetAllFiltersResponse> searchByBrandName(@RequestParam String brandName) {
        return service.searchByBrandName(brandName);
    }

    @GetMapping("/model-search")
    public List<GetAllFiltersResponse> searchByModelName(@RequestParam String modelName) {
        return service.searchByModelName(modelName);
    }

    @GetMapping("/year")
    public List<GetAllFiltersResponse> getByModelYear(@RequestParam int modelYear) {
        return service.getByModelYear(modelYear);
    }

    @GetMapping("/state")
    public List<GetAllFiltersResponse> getByState(@RequestParam int state) {
        return service.getByState(state);
    }
}
