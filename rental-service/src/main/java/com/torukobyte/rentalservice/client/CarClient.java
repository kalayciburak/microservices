package com.torukobyte.rentalservice.client;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "carclient", url = "http://localhost:9010/")
public interface CarClient {
    //    @PostMapping("inventory-service/api/v1/cars/checkIfCarAvailable/{id}")
    @RequestMapping(method = RequestMethod.GET, value = "inventory-service/api/v1/cars/checkIfCarAvailable/{id}")
    @Headers(value = "Content-Type: application/json")
    void checkIfCarAvailable(@PathVariable String id);
}
