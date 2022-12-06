package com.torukobyte.inventoryservice.business.dto.requests.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRequest {
    @Min(value = 1886)
    private int modelYear;
    @NotBlank
    @NotNull
    private String plate;
    @NotBlank
    @NotNull
    private String modelId;
}
