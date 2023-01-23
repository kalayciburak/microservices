package com.kodlamaio.invoiceservice.api.controllers;

import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.invoiceservice.bussines.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.bussines.dto.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.bussines.dto.requests.update.UpdateInvoiceRequest;
import com.kodlamaio.invoiceservice.bussines.dto.responses.create.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.get.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.get.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.update.UpdateInvoiceResponse;
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
@RequestMapping("/api/v1/invoices")
public class InvoicesController {
    private final InvoiceService invoiceService;

    @GetMapping
    @PreAuthorize("hasAnyRole('developer', 'moderator', 'admin')")
    public List<GetAllInvoicesResponse> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasAnyRole('developer', 'moderator', 'admin') or returnObject.customerId == #jwt.subject")
    public GetInvoiceResponse getById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return invoiceService.getById(id);
    }

    @PostMapping
    public CreateInvoiceResponse add(@Valid @RequestBody CreateInvoiceRequest request, @AuthenticationPrincipal Jwt jwt) {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setCustomerId(jwt.getClaimAsString("sub"));
        customerRequest.setCustomerUserName(jwt.getClaimAsString("preferred_username"));
        customerRequest.setCustomerFirstName(jwt.getClaimAsString("given_name"));
        customerRequest.setCustomerLastName(jwt.getClaimAsString("family_name"));
        customerRequest.setCustomerEmail(jwt.getClaimAsString("email"));

        return invoiceService.add(request, customerRequest);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('developer', 'admin')")
    public UpdateInvoiceResponse update(@Valid @RequestBody UpdateInvoiceRequest request, @PathVariable String id) {
        return invoiceService.update(request, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('developer', 'admin')")
    public void delete(@PathVariable String id) {
        invoiceService.delete(id);
    }
}