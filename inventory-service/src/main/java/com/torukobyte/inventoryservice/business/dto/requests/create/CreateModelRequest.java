package com.torukobyte.inventoryservice.business.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateModelRequest {
    @NotBlank
    @NotNull
    @Size(min = 2, max = 20)
    private String name;
    @NotBlank
    @NotNull
    private String brandId;
}
