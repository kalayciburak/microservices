package com.torukobyte.inventoryservice.business.abstracts;

import com.torukobyte.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.torukobyte.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.torukobyte.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetAllCarsResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetCarResponse;
import com.torukobyte.inventoryservice.business.dto.responses.update.UpdateCarResponse;

import java.util.List;

public interface CarService {
    List<GetAllCarsResponse> getAll();
    GetCarResponse getById(String id);
    CreateCarResponse add(CreateCarRequest request);
    UpdateCarResponse update(UpdateCarRequest request, String id);
    void delete(String id);
}

