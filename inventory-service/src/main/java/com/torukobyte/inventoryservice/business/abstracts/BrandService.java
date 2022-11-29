package com.torukobyte.inventoryservice.business.abstracts;

import com.torukobyte.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.torukobyte.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.torukobyte.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.torukobyte.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.torukobyte.inventoryservice.business.dto.responses.update.UpdateBrandResponse;

import java.util.List;

public interface BrandService {
    List<GetAllBrandsResponse> getAll();
    GetBrandResponse getById(String id);
    CreateBrandResponse add(CreateBrandRequest request);
    UpdateBrandResponse update(UpdateBrandRequest request, String id);
    void delete(String id);
}
