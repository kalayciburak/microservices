package com.kodlamaio.inventoryservice.business.concretes;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.events.inventories.brands.BrandDeletedEvent;
import com.kodlamaio.common.events.inventories.brands.BrandUpdatedEvent;
import com.kodlamaio.common.util.exceptions.BusinessException;
import com.kodlamaio.common.util.mapping.ModelMapperService;
import com.kodlamaio.inventoryservice.business.abstracts.BrandService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import com.kodlamaio.inventoryservice.entities.Brand;
import com.kodlamaio.inventoryservice.kafka.producer.InventoryProducer;
import com.kodlamaio.inventoryservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository repository;
    private final ModelMapperService mapper;
    private final InventoryProducer producer;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        List<Brand> brands = repository.findAll();
        List<GetAllBrandsResponse> response = brands
                .stream()
                .map(brand -> mapper.forResponse().map(brand, GetAllBrandsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetBrandResponse getById(String id) {
        checkIfBrandExistsById(id);
        Brand brand = repository.findById(id).orElseThrow();
        GetBrandResponse response = mapper.forResponse().map(brand, GetBrandResponse.class);

        return response;
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        checkIfBrandExistsByName(request.getName());
        Brand brand = mapper.forRequest().map(request, Brand.class);
        brand.setId(UUID.randomUUID().toString());
        repository.save(brand);
        CreateBrandResponse response = mapper.forResponse().map(brand, CreateBrandResponse.class);

        return response;
    }

    @Override
    public UpdateBrandResponse update(UpdateBrandRequest request, String id) {
        checkIfBrandExistsById(id);
        Brand brand = mapper.forRequest().map(request, Brand.class);
        brand.setId(id);
        repository.save(brand);
        UpdateBrandResponse response = mapper.forResponse().map(brand, UpdateBrandResponse.class);
        updateMongo(id, brand.getName());

        return response;
    }

    private void updateMongo(String id, String name) {
        BrandUpdatedEvent event = new BrandUpdatedEvent();
        event.setId(id);
        event.setName(name);
        producer.sendMessage(event);
    }

    @Override
    public void delete(String id) {
        checkIfBrandExistsById(id);
        repository.deleteById(id);
        deleteMongo(id);
    }

    private void checkIfBrandExistsById(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException(Messages.Brand.NotExists);
        }
    }

    private void deleteMongo(String id) {
        BrandDeletedEvent event = new BrandDeletedEvent();
        event.setBrandId(id);
        producer.sendMessage(event);
    }

    private void checkIfBrandExistsByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException(Messages.Brand.Exists);
        }
    }
}
