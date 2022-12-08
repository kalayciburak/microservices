package com.torukobyte.rentalservice.configuration.client;

import com.torukobyte.common.dto.CreateRentalPaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "paymentclient", url = "http://localhost:9010/")
public interface PaymentClient {
    @RequestMapping(method = RequestMethod.POST, value = "payment-service/api/v1/payments/check")
    void checkIfPaymentSuccessful(@RequestBody CreateRentalPaymentRequest request);
}
