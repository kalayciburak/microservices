package com.torukobyte.rentalservice.configuration.client;

import com.torukobyte.common.dto.GetCarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "carclient", url = "http://localhost:9010/")
public interface CarClient {
    @RequestMapping(method = RequestMethod.GET, value = "inventory-service/api/v1/cars/check-car-available/{id}")
    void checkIfCarAvailable(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET, value = "inventory-service/api/v1/cars/get-car-response/{id}")
    GetCarResponse getCarResponse(@PathVariable String id);
}