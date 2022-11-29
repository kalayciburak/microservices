package com.torukobyte.inventoryservice.business.concretes;

import com.torukobyte.common.util.exceptions.BusinessException;
import com.torukobyte.common.util.mapping.ModelMapperService;
import com.torukobyte.inventoryservice.business.abstracts.BrandService;
import com.torukobyte.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.torukobyte.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.torukobyte.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.torukobyte.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import com.torukobyte.inventoryservice.entities.Brand;
import com.torukobyte.inventoryservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository repository;
    private final ModelMapperService mapper;

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

        return response;
    }

    @Override
    public void delete(String id) {
        checkIfBrandExistsById(id);
        repository.deleteById(id);
    }

    private void checkIfBrandExistsByName(String name) {
        if (repository.existsByNameIgnoreCase(name)) {
            throw new BusinessException("BRAND.EXISTS");
        }
    }

    private void checkIfBrandExistsById(String id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("BRAND.NOT.EXISTS");
        }
    }
}
