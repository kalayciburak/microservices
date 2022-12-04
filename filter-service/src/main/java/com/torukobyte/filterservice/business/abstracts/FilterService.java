package com.torukobyte.filterservice.business.abstracts;

import com.torukobyte.filterservice.business.dto.responses.GetAllFiltersResponse;
import com.torukobyte.filterservice.entities.Filter;

import java.util.List;

public interface FilterService {
    List<GetAllFiltersResponse> getAll();
    List<GetAllFiltersResponse> getByBrandName(String brandName);
    List<GetAllFiltersResponse> getByModelName(String modelName);
    List<GetAllFiltersResponse> getByPlate(String plate);
    List<GetAllFiltersResponse> searchByPlate(String plate);
    List<GetAllFiltersResponse> searchByBrandName(String brandName);
    List<GetAllFiltersResponse> searchByModelName(String modelName);
    List<GetAllFiltersResponse> getByModelYear(int modelYear);

    // Consumer service
    Filter getByCarId(String id);
    List<Filter> getByModelId(String modelId);
    List<Filter> getByBrandId(String brandId);
    void save(Filter mongodb);
    void delete(String id);
    void deleteAllByBrandId(String brandId);
    void deleteAllByModelId(String modelId);
}
