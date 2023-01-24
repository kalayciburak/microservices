package com.kodlamaio.rentalservice.configuration.client;

import com.kodlamaio.common.constants.Clients;
import com.kodlamaio.common.dto.GetCarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = Clients.CarClient, url = Clients.BaseUrl)
public interface CarClient {
    @RequestMapping(method = RequestMethod.GET, value = Clients.CarClientCheckAvailable)
    void checkIfCarAvailable(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET, value = Clients.CarClientGetResponse)
    GetCarResponse getCarResponse(@PathVariable String id);
}