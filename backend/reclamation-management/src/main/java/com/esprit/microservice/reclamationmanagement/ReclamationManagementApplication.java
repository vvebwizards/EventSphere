package com.esprit.microservice.reclamationmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ReclamationManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReclamationManagementApplication.class, args);
    }

}
