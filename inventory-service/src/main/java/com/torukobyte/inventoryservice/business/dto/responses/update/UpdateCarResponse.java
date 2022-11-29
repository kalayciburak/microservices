package com.torukobyte.inventoryservice.business.dto.responses.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarResponse {
    private String id;
    private double dailyPrice;
    private int modelYear;
    private String plate;
    private int state;
    private String modelId;
}
