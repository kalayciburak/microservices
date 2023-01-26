package com.kodlamaio.paymentservice.api.controllers;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.common.constants.Roles;
import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.common.utils.jwt.customer.ParseJwtToCustomerRequest;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.update.UpdatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(Paths.Payment.Prefix)
public class PaymentsController {
    private final PaymentService service;

    @GetMapping
    @PreAuthorize(Roles.AdminOrDeveloper)
    public List<GetAllPaymentsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping(Paths.IdSuffix)
    @PostAuthorize(Roles.AdminOrDeveloper + "|| returnObject.customerId == #jwt.subject")
    public GetPaymentResponse getById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return service.getById(id);
    }

    @PostMapping
    public CreatePaymentResponse add(@Valid @RequestBody CreatePaymentRequest request, @AuthenticationPrincipal Jwt jwt) {
        CustomerRequest customerRequest = ParseJwtToCustomerRequest.getCustomerInformation(jwt);

        return service.add(request, customerRequest);
    }

    @PutMapping(Paths.IdSuffix)
    @PreAuthorize(Roles.AdminOrDeveloper)
    public UpdatePaymentResponse update(@Valid @RequestBody UpdatePaymentRequest request, @PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        CustomerRequest customerRequest = ParseJwtToCustomerRequest.getCustomerInformation(jwt);

        return service.update(request, id, customerRequest);
    }

    @DeleteMapping(Paths.IdSuffix)
    @PreAuthorize(Roles.AdminOrDeveloper)
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @PostMapping(Paths.Payment.CheckSuffix)
    public void checkIfPaymentSuccessful(@RequestBody CreateRentalPaymentRequest request) {
        service.checkIfPaymentSuccessful(request);
    }
}
