package com.esprit.microservice.resourcemanagement.config;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "user-service", url = "http://user-service:3000")
public interface  UserClient {
}
