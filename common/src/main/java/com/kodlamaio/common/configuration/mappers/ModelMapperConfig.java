package com.kodlamaio.common.configuration.mappers;

import com.kodlamaio.common.utils.mapping.ModelMapperManager;
import com.kodlamaio.common.utils.mapping.ModelMapperService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ModelMapperService getModelMapperService(ModelMapper mapper) {
        return new ModelMapperManager(mapper);
    }
}
