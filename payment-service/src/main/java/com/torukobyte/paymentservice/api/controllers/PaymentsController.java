package com.torukobyte.paymentservice.api.controllers;

import com.torukobyte.common.dto.CreateRentalPaymentRequest;
import com.torukobyte.paymentservice.business.abstracts.PaymentService;
import com.torukobyte.paymentservice.business.dto.requests.create.CreatePaymentRequest;
import com.torukobyte.paymentservice.business.dto.requests.update.UpdatePaymentRequest;
import com.torukobyte.paymentservice.business.dto.responses.create.CreatePaymentResponse;
import com.torukobyte.paymentservice.business.dto.responses.get.GetAllPaymentsResponse;
import com.torukobyte.paymentservice.business.dto.responses.get.GetPaymentResponse;
import com.torukobyte.paymentservice.business.dto.responses.update.UpdatePaymentResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public CreatePaymentResponse add(@Valid @RequestBody CreatePaymentRequest request) {
        return service.add(request);
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
