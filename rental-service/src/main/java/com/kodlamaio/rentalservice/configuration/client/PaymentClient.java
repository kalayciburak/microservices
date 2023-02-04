package com.kodlamaio.rentalservice.configuration.client;

import com.kodlamaio.common.constants.Clients;
import com.kodlamaio.common.dto.CreateRentalPaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = Clients.ProdPaymentName)
public interface PaymentClient {
    @RequestMapping(method = RequestMethod.POST, value = Clients.PaymentClientCheckProd)
    void checkIfPaymentSuccessful(@RequestBody CreateRentalPaymentRequest request);
}