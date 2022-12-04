package com.torukobyte.filterservice.business.abstracts;

import com.torukobyte.filterservice.entities.Filter;

import java.util.List;

public interface FilterService {
    List<Filter> getAll();
    List<Filter> getByBrandName(String brandName);
    List<Filter> getByModelName(String modelName);
    List<Filter> getByPlate(String plate);
    Filter getByCarId(String id);
    List<Filter> getByModelId(String modelId);
    List<Filter> getByBrandId(String brandId);
    void save(Filter mongodb);
    void delete(String id);
    void deleteAllByBrandId(String brandId);
    void deleteAllByModelId(String modelId);
}
