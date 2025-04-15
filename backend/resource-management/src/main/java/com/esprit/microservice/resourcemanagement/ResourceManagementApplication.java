package com.esprit.microservice.resourcemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients
public class ResourceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceManagementApplication.class, args);
    }

}
