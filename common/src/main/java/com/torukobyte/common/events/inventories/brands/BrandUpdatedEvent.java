package com.torukobyte.common.events.inventories.brands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandUpdatedEvent {
    private String id;
    private String name;
}
