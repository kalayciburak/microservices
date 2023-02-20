package com.kodlamaio.filterservice.business.concretes;

import com.kodlamaio.common.constants.Messages;
import com.kodlamaio.common.utils.mapping.ModelMapperService;
import com.kodlamaio.filterservice.business.abstracts.FilterService;
import com.kodlamaio.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.kodlamaio.filterservice.business.dto.responses.GetFilterResponse;
import com.kodlamaio.filterservice.entities.Filter;
import com.kodlamaio.filterservice.repository.FilterRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FilterManager implements FilterService {
    private final FilterRepository repository;
    private final ModelMapperService mapper;

    @Override
    public GetFilterResponse getByPlate(String plate) {
        checkIfExistByPlate(plate);
        Filter filter = repository.findByPlateIgnoreCase(plate);
        GetFilterResponse response = mapper.forResponse().map(filter, GetFilterResponse.class);

        return response;
    }

    @Override
    @Cacheable(value = "filters", key = "#root.methodName")
    public List<GetAllFiltersResponse> getAll() {
        return findAllAndMapToResponseList(repository.findAll());
    }

    @Override
    public List<GetAllFiltersResponse> getByBrandName(String brandName) {
        return findAllAndMapToResponseList(repository.findByBrandNameIgnoreCase(brandName));
    }

    @Override
    public List<GetAllFiltersResponse> getByModelName(String modelName) {
        return findAllAndMapToResponseList(repository.findByModelNameIgnoreCase(modelName));
    }

    @Override
    public List<GetAllFiltersResponse> searchByPlate(String plate) {
        return findAllAndMapToResponseList(repository.findByPlateContainingIgnoreCase(plate));
    }

    @Override
    public List<GetAllFiltersResponse> searchByBrandName(String brandName) {
        return findAllAndMapToResponseList(repository.findByBrandNameContainingIgnoreCase(brandName));
    }

    @Override
    public List<GetAllFiltersResponse> searchByModelName(String modelName) {
        return findAllAndMapToResponseList(repository.findByModelNameContainingIgnoreCase(modelName));
    }

    @Override
    public List<GetAllFiltersResponse> getByModelYear(int modelYear) {
        return findAllAndMapToResponseList(repository.findByModelYear(modelYear));
    }

    @Override
    public List<GetAllFiltersResponse> getByState(int state) {
        return findAllAndMapToResponseList(repository.findByState(state));
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
    public void save(Filter filter) {
        repository.save(filter);
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

    private void checkIfExistByPlate(String plate) {
        if (!repository.existsByPlate(plate)) {
            throw new RuntimeException(Messages.Filter.NotExists);
        }
    }

    private List<GetAllFiltersResponse> findAllAndMapToResponseList(List<Filter> repositoryFilterList) {
        List<Filter> filters = repositoryFilterList;
        List<GetAllFiltersResponse> response = filters
                .stream()
                .map(filter -> mapper.forResponse().map(filter, GetAllFiltersResponse.class))
                .toList();

        return response;
    }
}
