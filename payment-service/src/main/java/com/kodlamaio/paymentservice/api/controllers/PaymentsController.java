package com.kodlamaio.paymentservice.api.controllers;

import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.update.UpdatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentsController {
    private final PaymentService service;

    @GetMapping
    public List<GetAllPaymentsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetPaymentResponse getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public CreatePaymentResponse add(@Valid @RequestBody CreatePaymentRequest request, @AuthenticationPrincipal Jwt jwt) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerId(jwt.getClaimAsString("sub"));
        customerRequest.setCustomerUserName(jwt.getClaimAsString("preferred_username"));
        customerRequest.setCustomerFirstName(jwt.getClaimAsString("given_name"));
        customerRequest.setCustomerLastName(jwt.getClaimAsString("family_name"));
        customerRequest.setCustomerEmail(jwt.getClaimAsString("email"));

        return service.add(request, customerRequest);
    }

    @PutMapping("/{id}")
    public UpdatePaymentResponse update(@Valid @RequestBody UpdatePaymentRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PostMapping("/check")
    public void checkIfPaymentSuccessful(@RequestBody CreateRentalPaymentRequest request) {
        service.checkIfPaymentSuccessful(request);
    }
}
