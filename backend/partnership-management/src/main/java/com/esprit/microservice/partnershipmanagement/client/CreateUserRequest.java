package com.esprit.microservice.partnershipmanagement.client;

public record CreateUserRequest(
        String username,
        String email,
        String password
) {}
