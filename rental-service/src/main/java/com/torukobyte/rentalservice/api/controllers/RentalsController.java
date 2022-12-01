package com.torukobyte.rentalservice.api.controllers;

import com.torukobyte.rentalservice.business.abstracts.RentalService;
import com.torukobyte.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.torukobyte.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.torukobyte.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/rentals")
public class RentalsController {
    private final RentalService service;

    @GetMapping
    public List<GetAllRentalsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetRentalResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public CreateRentalResponse add(@RequestBody CreateRentalRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateRentalResponse update(@RequestBody UpdateRentalRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
