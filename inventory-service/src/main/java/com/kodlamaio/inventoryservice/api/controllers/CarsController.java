package com.kodlamaio.inventoryservice.api.controllers;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.common.constants.Roles;
import com.kodlamaio.inventoryservice.business.abstracts.CarService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(Paths.Inventory.Car.Prefix)
public class CarsController {
    private final CarService service;

    @GetMapping
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')" + " || hasRole('" + Roles.Moderator + "')")
    public List<GetAllCarsResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public CreateCarResponse add(@Valid @RequestBody CreateCarRequest request) {
        return service.add(request);
    }

    @PutMapping(Paths.IdSuffix)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public UpdateCarResponse update(@Valid @RequestBody UpdateCarRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping(Paths.IdSuffix)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping(Paths.IdSuffix)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')" + " || hasRole('" + Roles.Moderator + "')")
    public GetCarResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping(Paths.Inventory.Car.CheckAvailableSuffix)
    public void checkIfCarAvailable(@PathVariable String id) {
        service.checkIfCarAvailable(id);
    }

    @GetMapping(Paths.Inventory.Car.GetResponseSuffix)
    public GetCarResponse getCarResponse(@PathVariable String id) {
        return service.getById(id);
    }
}