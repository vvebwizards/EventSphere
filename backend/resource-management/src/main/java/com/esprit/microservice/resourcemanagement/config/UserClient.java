package com.esprit.microservice.resourcemanagement.config;

import com.esprit.microservice.resourcemanagement.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",configuration = FeignClientConfig.class)
public interface  UserClient {
    @GetMapping("/user/getMe")
    ResponseEntity<UserDTO> getUserById();

}
