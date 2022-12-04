package com.torukobyte.inventoryservice.business.concretes;

import com.torukobyte.common.events.inventories.models.ModelDeletedEvent;
import com.torukobyte.common.events.inventories.models.ModelUpdatedEvent;
import com.torukobyte.common.util.exceptions.BusinessException;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.inventoryservice.business.abstracts.ModelService;
import com.torukobyte.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.torukobyte.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.torukobyte.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.torukobyte.inventoryservice.business.dto.responses.update.UpdateModelResponse;
import com.torukobyte.inventoryservice.entities.Model;
import com.torukobyte.inventoryservice.kafka.producer.InventoryProducer;
import com.torukobyte.inventoryservice.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelManager implements ModelService {
    private final ModelRepository repository;
    private final ModelMapperService mapper;
    private final InventoryProducer producer;

    @Override
    public List<GetAllModelsResponse> getAll() {
        List<Model> models = repository.findAll();
        List<GetAllModelsResponse> response = models
                .stream()
                .map(model -> mapper.forResponse().map(model, GetAllModelsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetModelResponse getById(String id) {
        checkIfExistsById(id);
        Model model = repository.findById(id).orElseThrow();
        GetModelResponse response = mapper.forResponse().map(model, GetModelResponse.class);

        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        checkIfExistsByName(request.getName());
        Model model = mapper.forRequest().map(request, Model.class);
        model.setId(UUID.randomUUID().toString());
        repository.save(model);
        CreateModelResponse response = mapper.forResponse().map(model, CreateModelResponse.class);

        return response;
    }

    @Override
    public UpdateModelResponse update(UpdateModelRequest request, String id) {
        checkIfExistsById(id);
        checkIfExistsByName(request.getName());
        Model model = mapper.forRequest().map(request, Model.class);
        model.setId(id);
        repository.save(model);
        UpdateModelResponse response = mapper.forResponse().map(model, UpdateModelResponse.class);
        updateMongo(id, request.getName(), request.getBrandId());

        return response;
    }

    @Override
    public void delete(String id) {
        checkIfExistsById(id);
        repository.deleteById(id);
        deleteMongo(id);
    }

    private void checkIfExistsById(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("MODEL.NOT.EXISTS");
        }
    }

    private void checkIfExistsByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException("MODEL.EXISTS");
        }
    }

    private void updateMongo(String id, String name, String brandId) {
        ModelUpdatedEvent event = new ModelUpdatedEvent();
        event.setId(id);
        event.setName(name);
        event.setBrandId(brandId);
        producer.sendMessage(event);
    }

    private void deleteMongo(String id) {
        ModelDeletedEvent event = new ModelDeletedEvent();
        event.setModelId(id);
        producer.sendMessage(event);
    }
}
