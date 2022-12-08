package com.kodlamaio.inventoryservice.business.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
    @NotBlank
    @NotNull
    private String modelId;
    @Min(value = 1886)
    private int modelYear;
    @NotBlank
    @NotNull
    private String plate;
    @Min(value = 1)
    @Max(value = 3)
    private int state;
    @Min(value = 0)
    private double dailyPrice;
}
