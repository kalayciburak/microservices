package com.torukobyte.invoiceservice.kafka;

import com.torukobyte.common.events.payments.PaymentReceivedEvent;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.invoiceservice.bussines.abstracts.InvoiceService;
import com.torukobyte.invoiceservice.configuration.client.CarClient;
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
    private final CarClient client;

    public RentalConsumer(InvoiceService service, ModelMapperService mapper, CarClient client) {
        this.service = service;
        this.mapper = mapper;
        this.client = client;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            , groupId = "payment-received"
    )
    public void consume(PaymentReceivedEvent event) {
        Invoice invoice = mapper.forRequest().map(event, Invoice.class);
        invoice.setDailyPrice(event.getDailyPrice());
        invoice.setTotalPrice(event.getTotalPrice());
        invoice.setBrandName(client.getCarResponse(event.getCarId()).getBrandName());
        invoice.setModelName(client.getCarResponse(event.getCarId()).getModelName());
        invoice.setModelYear(client.getCarResponse(event.getCarId()).getModelYear());
        service.createInvoice(invoice);
        LOGGER.info("Invoice created for : {}", event.getFullName());
    }
}