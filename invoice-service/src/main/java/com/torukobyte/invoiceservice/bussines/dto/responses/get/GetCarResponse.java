package com.torukobyte.invoiceservice.bussines.dto.responses.get;

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
    private double dailyPrice;
    private int modelYear;
    private String plate;
    private int state;
    private String brandName;
    private String modelName;
}