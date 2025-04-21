package com.esprit.microservice.reclamationmanagement.configuration;

import com.esprit.microservice.reclamationmanagement.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service",configuration = FeignClientConfig.class)
public interface  UserClient {
    @GetMapping("/user/getMe")
    ResponseEntity<UserDTO> getUserById();

}