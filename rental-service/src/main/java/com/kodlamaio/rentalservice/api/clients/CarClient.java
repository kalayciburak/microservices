package com.kodlamaio.rentalservice.api.clients;

import com.kodlamaio.common.constants.Clients;
import com.kodlamaio.common.dto.GetCarResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = Clients.ProdInventoryName)
public interface CarClient {
    @RequestMapping(method = RequestMethod.GET, value = Clients.CarClientCheckAvailableProd)
    void checkIfCarAvailable(@PathVariable String id);

    @RequestMapping(method = RequestMethod.GET, value = Clients.CarClientGetResponseProd)
    GetCarResponse getCarResponse(@PathVariable String id);
}