package com.kodlamaio.invoiceservice.api.controllers;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.common.constants.Roles;
import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.common.utils.jwt.customer.ParseJwtToCustomerRequest;
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
@RequestMapping(Paths.Invoice.Prefix)
public class InvoicesController {
    private final InvoiceService invoiceService;

    @GetMapping
    @PreAuthorize(Roles.AdminOrDeveloperOrModerator)
    public List<GetAllInvoicesResponse> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping(Paths.IdSuffix)
    @PostAuthorize(Roles.AdminOrDeveloperOrModerator + "|| returnObject.customerId == #jwt.subject")
    public GetInvoiceResponse getById(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return invoiceService.getById(id);
    }

    @PostMapping
    @PreAuthorize(Roles.AdminOrDeveloperOrModerator)
    public CreateInvoiceResponse add(@Valid @RequestBody CreateInvoiceRequest request, @AuthenticationPrincipal Jwt jwt) {
        CustomerRequest customerRequest = ParseJwtToCustomerRequest.getCustomerInformation(jwt);

        return invoiceService.add(request, customerRequest);
    }

    @PutMapping(Paths.IdSuffix)
    @PreAuthorize(Roles.AdminOrDeveloper)
    public UpdateInvoiceResponse update(@Valid @RequestBody UpdateInvoiceRequest request, @PathVariable String id) {
        return invoiceService.update(request, id);
    }

    @DeleteMapping(Paths.IdSuffix)
    @PreAuthorize(Roles.AdminOrDeveloper)
    public void delete(@PathVariable String id) {
        invoiceService.delete(id);
    }
}