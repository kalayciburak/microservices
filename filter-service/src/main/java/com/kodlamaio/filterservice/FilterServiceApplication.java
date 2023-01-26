package com.kodlamaio.filterservice;

import com.kodlamaio.common.constants.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableCaching
@EnableDiscoveryClient
@EnableElasticsearchRepositories(basePackages = Paths.ElasticsearchRepositoriesBasePackage)
@SpringBootApplication(scanBasePackages = {Paths.ExceptionHandlerBasePackage, Paths.Filter.ServiceBasePackage})
public class FilterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilterServiceApplication.class, args);
    }
}
