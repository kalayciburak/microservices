package com.torukobyte.rentalservice.api.controllers;

import com.torukobyte.rentalservice.business.abstracts.RentalService;
import com.torukobyte.rentalservice.business.dto.requests.create.CreatePaymentRequest;
import com.torukobyte.rentalservice.business.dto.requests.create.CreateRentalRequest;
import com.torukobyte.rentalservice.business.dto.requests.update.UpdateRentalRequest;
import com.torukobyte.rentalservice.business.dto.responses.create.CreateRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetAllRentalsResponse;
import com.torukobyte.rentalservice.business.dto.responses.get.GetRentalResponse;
import com.torukobyte.rentalservice.business.dto.responses.update.UpdateRentalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/rentals")
public class RentalsController {
    private final RentalService service;
    Logger logger = LoggerFactory.getLogger(RentalsController.class);

    public RentalsController(RentalService service) {
        this.service = service;
    }

    @GetMapping
    public List<GetAllRentalsResponse> getAll() {
        logger.info("Getting all rentals");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetRentalResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public CreateRentalResponse add(
            @Valid @RequestBody CreateRentalRequest request,
            @RequestParam String cardNumber,
            @RequestParam String fullName,
            @RequestParam int cardExpirationYear,
            @RequestParam int cardExpirationMonth,
            @RequestParam String cardCvv) {
        logger.info("Adding new rental");
        CreatePaymentRequest paymentRequest =
                new CreatePaymentRequest(cardNumber,
                                         fullName,
                                         cardExpirationYear,
                                         cardExpirationMonth,
                                         cardCvv);
        return service.add(request, paymentRequest);
    }

    @PutMapping("/{id}")
    public UpdateRentalResponse update(@Valid @RequestBody UpdateRentalRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }
}
