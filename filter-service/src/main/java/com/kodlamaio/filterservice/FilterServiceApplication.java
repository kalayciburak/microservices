package com.kodlamaio.filterservice;

import com.kodlamaio.common.constants.Paths;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableElasticsearchRepositories(basePackages = Paths.ElasticsearchRepositoriesBasePackage)
public class FilterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FilterServiceApplication.class, args);
    }
}
