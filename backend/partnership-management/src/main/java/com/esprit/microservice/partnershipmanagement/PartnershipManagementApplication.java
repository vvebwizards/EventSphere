package com.esprit.microservice.partnershipmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PartnershipManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartnershipManagementApplication.class, args);
	}

}
