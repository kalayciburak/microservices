package com.torukobyte.invoiceservice.repository;

import com.torukobyte.invoiceservice.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
}