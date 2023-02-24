package com.kodlamaio.inventoryservice.business.concretes;

import com.kodlamaio.common.events.inventories.models.ModelDeletedEvent;
import com.kodlamaio.common.events.inventories.models.ModelUpdatedEvent;
import com.kodlamaio.common.utils.mapping.ModelMapperService;
import com.kodlamaio.inventoryservice.business.abstracts.ModelService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateModelResponse;
import com.kodlamaio.inventoryservice.business.rules.ModelBusinessRules;
import com.kodlamaio.inventoryservice.entities.Model;
import com.kodlamaio.inventoryservice.kafka.producer.InventoryProducer;
import com.kodlamaio.inventoryservice.repository.ModelRepository;
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
    private final ModelBusinessRules rules;

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
        rules.checkIfExistsById(id);
        Model model = repository.findById(id).orElseThrow();
        GetModelResponse response = mapper.forResponse().map(model, GetModelResponse.class);

        return response;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        rules.checkIfExistsByName(request.getName());
        Model model = mapper.forRequest().map(request, Model.class);
        model.setId(UUID.randomUUID().toString());
        repository.save(model);
        CreateModelResponse response = mapper.forResponse().map(model, CreateModelResponse.class);

        return response;
    }

    @Override
    public UpdateModelResponse update(UpdateModelRequest request, String id) {
        rules.checkIfExistsById(id);
        rules.checkIfExistsByName(request.getName());
        Model model = mapper.forRequest().map(request, Model.class);
        model.setId(id);
        repository.save(model);
        UpdateModelResponse response = mapper.forResponse().map(model, UpdateModelResponse.class);
        updateMongo(id, request.getName(), request.getBrandId());

        return response;
    }

    @Override
    public void delete(String id) {
        rules.checkIfExistsById(id);
        repository.deleteById(id);
        deleteMongo(id);
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
