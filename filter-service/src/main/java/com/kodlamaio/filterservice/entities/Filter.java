package com.kodlamaio.filterservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "filter-db")
public class Filter {
    @Id
    private String id;
    @Field(name = "carId")
    private String carId;
    @Field(name = "modelId")
    private String modelId;
    @Field(name = "brandId")
    private String brandId;
    @Field(name = "modelName")
    private String modelName;
    @Field(name = "brandName")
    private String brandName;
    @Field(name = "modelYear")
    private int modelYear;
    @Field(name = "plate")
    private String plate;
    @Field(name = "state")
    private int state;
    @Field(name = "dailyPrice")
    private int dailyPrice;
}
