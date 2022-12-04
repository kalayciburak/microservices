package com.torukobyte.invoiceservice.bussines.abstracts;

import com.torukobyte.invoiceservice.bussines.dto.requests.create.CreateInvoiceRequest;
import com.torukobyte.invoiceservice.bussines.dto.requests.update.UpdateInvoiceRequest;
import com.torukobyte.invoiceservice.bussines.dto.responses.create.CreateInvoiceResponse;
import com.torukobyte.invoiceservice.bussines.dto.responses.get.GetAllInvoicesResponse;
import com.torukobyte.invoiceservice.bussines.dto.responses.get.GetInvoiceResponse;
import com.torukobyte.invoiceservice.bussines.dto.responses.update.UpdateInvoiceResponse;
import com.torukobyte.invoiceservice.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    List<GetAllInvoicesResponse> getAll();
    GetInvoiceResponse getById(String id);
    CreateInvoiceResponse add(CreateInvoiceRequest request);
    UpdateInvoiceResponse update(UpdateInvoiceRequest request, String id);
    void delete(String id);
    void createInvoice(Invoice invoice);
}