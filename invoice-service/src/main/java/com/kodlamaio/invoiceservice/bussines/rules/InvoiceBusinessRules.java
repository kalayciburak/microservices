package com.kodlamaio.invoiceservice.bussines.rules;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.utils.exceptions.BusinessException;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvoiceBusinessRules {
    private final InvoiceRepository repository;

    public void checkIfInvoiceExists(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Invoice.NotFound);
        }
    }
}
