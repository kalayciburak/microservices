package com.kodlamaio.invoiceservice.api.controllers;

import com.kodlamaio.invoiceservice.bussines.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.bussines.dto.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.bussines.dto.requests.update.UpdateInvoiceRequest;
import com.kodlamaio.invoiceservice.bussines.dto.responses.create.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.get.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.get.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.update.UpdateInvoiceResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/invoices")
public class InvoicesController {
    private final InvoiceService invoiceService;

    @GetMapping
    public List<GetAllInvoicesResponse> getAll() {
        return invoiceService.getAll();
    }

    @GetMapping("/{id}")
    public GetInvoiceResponse getById(@PathVariable String id) {
        return invoiceService.getById(id);
    }

    @PostMapping
    public CreateInvoiceResponse add(@Valid @RequestBody CreateInvoiceRequest request) {
        return invoiceService.add(request);
    }

    @PutMapping("/{id}")
    public UpdateInvoiceResponse update(@Valid @RequestBody UpdateInvoiceRequest request, @PathVariable String id) {
        return invoiceService.update(request, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        invoiceService.delete(id);
    }
}