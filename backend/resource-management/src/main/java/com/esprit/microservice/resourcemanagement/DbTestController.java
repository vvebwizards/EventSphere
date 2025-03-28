package com.esprit.microservice.resourcemanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-db")
    public String testDbConnection() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return "PostgreSQL connection successful!";
        } catch (Exception e) {
            return "PostgreSQL connection failed: " + e.getMessage();
        }
    }
}
