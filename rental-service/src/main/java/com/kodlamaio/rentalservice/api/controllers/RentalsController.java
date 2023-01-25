package com.kodlamaio.rentalservice.api.controllers;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.common.constants.Roles;
import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.common.utils.jwt.customer.ParseJwtToCustomerRequest;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(Paths.Rental.Prefix)
public class RentalsController {
    private final RentalService service;

    public RentalsController(RentalService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize(Roles.AdminOrDeveloperOrModerator)
    public List<GetAllRentalsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(Paths.IdSuffix)
    @PostAuthorize(Roles.AdminOrDeveloperOrModerator + "|| returnObject.customerId == #jwt.subject")
    public GetRentalResponse getById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return service.getById(id);
    }

    @PostMapping
    public CreateRentalResponse add(@Valid @RequestBody CreateRentalRequest request, @AuthenticationPrincipal Jwt jwt) {
        CustomerRequest customerRequest = ParseJwtToCustomerRequest.getCustomerInformation(jwt);

        return service.add(request, customerRequest);
    }

    @PutMapping(Paths.IdSuffix)
    @PreAuthorize(Roles.AdminOrDeveloper)
    public UpdateRentalResponse update(@Valid @RequestBody UpdateRentalRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping(Paths.IdSuffix)
    @PreAuthorize(Roles.AdminOrDeveloper)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
