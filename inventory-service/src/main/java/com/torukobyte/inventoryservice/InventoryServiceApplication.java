package com.torukobyte.inventoryservice;

import com.torukobyte.common.util.mapping.ModelMapperManager;
import com.torukobyte.common.util.mapping.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapperService getModelMapperService(ModelMapper mapper) {
        return new ModelMapperManager(mapper);
    }
}
