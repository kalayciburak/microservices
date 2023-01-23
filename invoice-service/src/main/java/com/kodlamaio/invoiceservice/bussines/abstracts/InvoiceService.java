package com.kodlamaio.invoiceservice.bussines.abstracts;

import com.kodlamaio.common.dto.CustomerRequest;
import com.kodlamaio.invoiceservice.bussines.dto.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.bussines.dto.requests.update.UpdateInvoiceRequest;
import com.kodlamaio.invoiceservice.bussines.dto.responses.create.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.get.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.get.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.bussines.dto.responses.update.UpdateInvoiceResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    List<GetAllInvoicesResponse> getAll();
    GetInvoiceResponse getById(String id);
    CreateInvoiceResponse add(CreateInvoiceRequest request, CustomerRequest customerRequest);
    UpdateInvoiceResponse update(UpdateInvoiceRequest request, String id);
    void delete(String id);
    void createInvoice(Invoice invoice);
}