package com.torukobyte.inventoryservice.business.abstracts;

import com.torukobyte.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.torukobyte.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.torukobyte.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.torukobyte.inventoryservice.business.dto.responses.update.UpdateModelResponse;

import java.util.List;

public interface ModelService {
    List<GetAllModelsResponse> getAll();
    GetModelResponse getById(String id);
    CreateModelResponse add(CreateModelRequest request);
    UpdateModelResponse update(UpdateModelRequest request, String id);
    void delete(String id);
}
