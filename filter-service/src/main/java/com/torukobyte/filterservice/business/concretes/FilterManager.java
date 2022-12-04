package com.torukobyte.filterservice.business.concretes;

import com.torukobyte.filterservice.business.abstracts.FilterService;
import com.torukobyte.filterservice.entities.Filter;
import com.torukobyte.filterservice.repository.FilterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FilterManager implements FilterService {
    private final FilterRepository repository;

    @Override
    public List<Filter> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Filter> getByBrandName(String brandName) {
        return repository.findByBrandNameIgnoreCase(brandName);
    }

    @Override
    public List<Filter> getByModelName(String modelName) {
        return repository.findByModelNameIgnoreCase(modelName);
    }

    @Override
    public List<Filter> getByPlate(String plate) {
        return repository.findByPlateIgnoreCase(plate);
    }

    @Override
    public Filter getByCarId(String id) {
        return repository.findByCarId(id);
    }

    @Override
    public List<Filter> getByModelId(String modelId) {
        return repository.findByModelId(modelId);
    }

    @Override
    public List<Filter> getByBrandId(String brandId) {
        return repository.findByBrandId(brandId);
    }

    @Override
    public void save(Filter mongodb) {
        repository.save(mongodb);
    }

    @Override
    public void delete(String id) {
        repository.deleteByCarId(id);
    }

    @Override
    public void deleteAllByBrandId(String brandId) {
        repository.deleteAllByBrandId(brandId);
    }

    @Override
    public void deleteAllByModelId(String modelId) {
        repository.deleteAllByModelId(modelId);
    }
}
