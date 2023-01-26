package com.kodlamaio.filterservice.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllFiltersResponse implements Serializable {
    private String id;
    private String carId;
    private String modelId;
    private String brandId;
    private String modelName;
    private String brandName;
    private int modelYear;
    private String plate;
    private int state;
    private int dailyPrice;
}
