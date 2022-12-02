package com.torukobyte.inventoryservice.api.controllers;

import com.torukobyte.inventoryservice.business.abstracts.CarService;
import com.torukobyte.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.torukobyte.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.torukobyte.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.torukobyte.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/cars")
public class CarsController {
    private final CarService service;

    @GetMapping
    public List<GetAllCarsResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public CreateCarResponse add(@RequestBody CreateCarRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateCarResponse update(@RequestBody UpdateCarRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping("/{id}")
    public GetCarResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("/check-car-available/{id}")
    public void checkIfCarAvailable(@PathVariable String id) {
        service.checkIfCarAvailable(id);
    }
}