package com.kodlamaio.inventoryservice.api.controllers;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.common.constants.Roles;
import com.kodlamaio.inventoryservice.business.abstracts.BrandService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllBrandsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetBrandResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(Paths.Inventory.Brand.Prefix)
public class BrandsController {
    private final BrandService service;

    @GetMapping
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')" + " || hasRole('" + Roles.Moderator + "')")
    public List<GetAllBrandsResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public CreateBrandResponse add(@Valid @RequestBody CreateBrandRequest request) {
        return service.add(request);
    }

    @PutMapping(Paths.IdSuffix)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public UpdateBrandResponse update(@Valid @RequestBody UpdateBrandRequest request, @PathVariable String id) {
        return service.update(request, id);
    }

    @DeleteMapping(Paths.IdSuffix)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public void delete(@PathVariable String id) {
        service.delete(id);
    }

    @GetMapping(Paths.IdSuffix)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')" + " || hasRole('" + Roles.Moderator + "')")
    public GetBrandResponse getById(@PathVariable String id) {
        return service.getById(id);
    }
}
