package com.esprit.microservice.partnershipmanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "user-service",
        url  = "${user-service.base-url}")
public interface UserClient {

    @GetMapping("/users/{id}")
    UserDto getById(@PathVariable("id") String id);

    @PostMapping("/users")
    UserDto create(@RequestBody CreateUserRequest request);
}
