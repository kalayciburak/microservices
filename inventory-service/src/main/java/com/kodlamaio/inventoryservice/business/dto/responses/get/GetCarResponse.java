package com.kodlamaio.inventoryservice.business.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetCarResponse {
    private String id;
    private int modelYear;
    private String plate;
    private int state;
    private double dailyPrice;
    private String brandName;
    private String modelName;
}
