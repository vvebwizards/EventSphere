package com.esprit.microservice.resourcemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String role;
    private String joined_at;
    private String updated_at;
}
