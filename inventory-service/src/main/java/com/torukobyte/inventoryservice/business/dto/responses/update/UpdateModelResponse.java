package com.torukobyte.inventoryservice.business.dto.responses.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateModelResponse {
    private String id;
    private String name;
    private String brandId;
}
