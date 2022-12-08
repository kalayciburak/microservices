package com.kodlamaio.inventoryservice.business.dto.responses.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarResponse {
    private String id;
    private String modelId;
    private int modelYear;
    private String plate;
    private int state;
    private double dailyPrice;
}
