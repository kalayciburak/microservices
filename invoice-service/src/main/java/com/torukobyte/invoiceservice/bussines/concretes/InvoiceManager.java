package com.torukobyte.invoiceservice.bussines.concretes;

import com.torukobyte.common.util.exceptions.BusinessException;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.invoiceservice.bussines.abstracts.InvoiceService;
import com.torukobyte.invoiceservice.bussines.dto.requests.create.CreateInvoiceRequest;
import com.torukobyte.invoiceservice.bussines.dto.requests.update.UpdateInvoiceRequest;
import com.torukobyte.invoiceservice.bussines.dto.responses.create.CreateInvoiceResponse;
import com.torukobyte.invoiceservice.bussines.dto.responses.get.GetAllInvoicesResponse;
import com.torukobyte.invoiceservice.bussines.dto.responses.get.GetInvoiceResponse;
import com.torukobyte.invoiceservice.bussines.dto.responses.update.UpdateInvoiceResponse;
import com.torukobyte.invoiceservice.entities.Invoice;
import com.torukobyte.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private final InvoiceRepository repository;
    private final ModelMapperService mapper;

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoices = repository.findAll();
        List<GetAllInvoicesResponse> response = invoices
                .stream()
                .map(invoice -> mapper.forResponse().map(invoice, GetAllInvoicesResponse.class)
                    ).toList();

        return response;
    }

    @Override
    public GetInvoiceResponse getById(String id) {
        checkIfInvoiceExists(id);
        Invoice invoice = repository.findById(id).orElseThrow();
        GetInvoiceResponse response = mapper.forResponse().map(invoice, GetInvoiceResponse.class);

        return response;
    }

    @Override
    public CreateInvoiceResponse add(CreateInvoiceRequest request) {
        Invoice invoice = mapper.forRequest().map(request, Invoice.class);
        invoice.setId(UUID.randomUUID().toString());
        repository.save(invoice);
        CreateInvoiceResponse response = mapper.forResponse().map(invoice, CreateInvoiceResponse.class);

        return response;
    }

    @Override
    public UpdateInvoiceResponse update(UpdateInvoiceRequest request, String id) {
        checkIfInvoiceExists(id);
        Invoice invoice = mapper.forRequest().map(request, Invoice.class);
        invoice.setId(id);
        repository.save(invoice);
        UpdateInvoiceResponse response = mapper.forResponse().map(invoice, UpdateInvoiceResponse.class);

        return response;
    }

    @Override
    public void delete(String id) {
        checkIfInvoiceExists(id);
        repository.deleteById(id);
    }

    @Override
    public void createInvoice(Invoice invoice) {
        invoice.setId(UUID.randomUUID().toString());
        repository.save(invoice);
    }

    private void checkIfInvoiceExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("INVOICE_NOT_FOUND");
        }
    }
}