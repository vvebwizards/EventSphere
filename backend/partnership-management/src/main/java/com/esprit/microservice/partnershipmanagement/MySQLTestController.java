package com.esprit.microservice.partnershipmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MySQLTestController {
    @Autowired
    private TestRepository testRepository;

    @GetMapping("/test-mysql")
    public String testMySQLConnection() {
        try {
            // Save a test entity
            TestEntity entity = new TestEntity(1L, "MySQLUser");
            testRepository.save(entity);

            // Retrieve it
            TestEntity retrieved = testRepository.findById(1L).orElse(null);
            if (retrieved != null) {
                return "MySQL connection successful! Saved and retrieved: " + retrieved.getName();
            } else {
                return "MySQL connection failed: Could not retrieve saved entity";
            }
        } catch (Exception e) {
            return "MySQL connection failed: " + e.getMessage();
        }
    }
}
