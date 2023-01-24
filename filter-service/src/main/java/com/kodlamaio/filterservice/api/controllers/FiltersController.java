package com.kodlamaio.filterservice.api.controllers;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.responses.GetFilterResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(Paths.Filter.Prefix)
public class FiltersController {
    private final FilterService service;

    @GetMapping
    public List<GetAllFiltersResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(Paths.Filter.GetByBrandNameSuffix)
    public List<GetAllFiltersResponse> getByBrandName(@RequestParam String brand) {
        return service.getByBrandName(brand);
    }

    @GetMapping(Paths.Filter.GetByModelNameSuffix)
    public List<GetAllFiltersResponse> getByModelName(@RequestParam String model) {
        return service.getByModelName(model);
    }

    @GetMapping(Paths.Filter.GetByPlateSuffix)
    public GetFilterResponse getByPlate(@RequestParam String plate) {
        return service.getByPlate(plate);
    }

    @GetMapping(Paths.Filter.GetByModelYearSuffix)
    public List<GetAllFiltersResponse> getByModelYear(@RequestParam int modelYear) {
        return service.getByModelYear(modelYear);
    }

    @GetMapping(Paths.Filter.GetByStateSuffix)
    public List<GetAllFiltersResponse> getByState(@RequestParam int state) {
        return service.getByState(state);
    }

    @GetMapping(Paths.Filter.PlateSearchSuffix)
    public List<GetAllFiltersResponse> searchByPlate(@RequestParam String plate) {
        return service.searchByPlate(plate);
    }

    @GetMapping(Paths.Filter.BrandSearchSuffix)
    public List<GetAllFiltersResponse> searchByBrandName(@RequestParam String brand) {
        return service.searchByBrandName(brand);
    }

    @GetMapping(Paths.Filter.ModelSearchSuffix)
    public List<GetAllFiltersResponse> searchByModelName(@RequestParam String model) {
        return service.searchByModelName(model);
    }
}
