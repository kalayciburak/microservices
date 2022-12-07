package com.torukobyte.invoiceservice.kafka;

import com.torukobyte.common.events.payments.PaymentReceivedEvent;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.invoiceservice.bussines.abstracts.InvoiceService;
import com.torukobyte.invoiceservice.entities.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RentalConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RentalConsumer.class);
    private final InvoiceService service;
    private final ModelMapperService mapper;

    public RentalConsumer(InvoiceService service, ModelMapperService mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            , groupId = "payment-received"
    )
    public void consume(PaymentReceivedEvent event) {
        Invoice invoice = mapper.forRequest().map(event, Invoice.class);
        service.createInvoice(invoice);
        LOGGER.info("Invoice created for : {}", event.getFullName());
    }
}