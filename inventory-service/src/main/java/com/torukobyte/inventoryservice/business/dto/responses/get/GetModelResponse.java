package com.torukobyte.inventoryservice.business.dto.responses.get;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetModelResponse {
    private String id;
    private String name;
    private String brandId;
    private String brandName;
}
