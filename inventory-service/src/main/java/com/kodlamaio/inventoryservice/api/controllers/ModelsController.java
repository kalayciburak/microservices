package com.kodlamaio.inventoryservice.api.controllers;

import com.kodlamaio.common.constants.Paths;
import com.kodlamaio.common.constants.Roles;
import com.kodlamaio.inventoryservice.business.abstracts.ModelService;
import com.kodlamaio.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.kodlamaio.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetAllModelsResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.get.GetModelResponse;
import com.kodlamaio.inventoryservice.business.dto.responses.update.UpdateModelResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(Paths.Inventory.Model.Prefix)
public class ModelsController {
    private final ModelService service;

    @GetMapping
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')" + " || hasRole('" + Roles.Moderator + "')")
    public List<GetAllModelsResponse> getAll() {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public CreateModelResponse add(@Valid @RequestBody CreateModelRequest request) {
        return service.add(request);
    }

    @PutMapping(Paths.IdSuffix)
    @PreAuthorize("hasRole('" + Roles.Admin + "')" + " || hasRole('" + Roles.Developer + "')")
    public UpdateModelResponse update(@Valid @RequestBody UpdateModelRequest request, @PathVariable String id) {
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
    public GetModelResponse getById(@PathVariable String id) {
        return service.getById(id);
    }
}