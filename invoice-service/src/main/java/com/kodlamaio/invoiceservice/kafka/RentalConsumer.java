package com.kodlamaio.invoiceservice.kafka;

import com.kodlamaio.common.constants.Events;
import com.kodlamaio.common.events.payments.PaymentReceivedEvent;
import com.kodlamaio.common.utils.mapping.ModelMapperService;
import com.kodlamaio.invoiceservice.bussines.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.entities.Invoice;
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
            topics = Events.Producer.Rental.PaymentReceived
            , groupId = Events.Consumer.Rental.PaymentReceivedGroupId
    )
    public void consume(PaymentReceivedEvent event) {
        Invoice invoice = mapper.forRequest().map(event, Invoice.class);
        service.createInvoice(invoice);
        LOGGER.info(Events.Logs.Consumer.Rental.InvoiceCreated, event.getCardholder());
    }
}