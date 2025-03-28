package com.esprit.microservice.eventmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoTestController {
    @Autowired
    private TestRepository testRepository;

    @GetMapping("/test-mongo")
    public String testMongoConnection() {
        try {
            // Insert a test document
            TestDocument doc = new TestDocument("MongoUser");
            testRepository.save(doc);

            // Retrieve it
            TestDocument retrieved = testRepository.findById(doc.getId()).orElse(null);
            if (retrieved != null) {
                return "MongoDB connection successful! Saved and retrieved: " + retrieved.getName();
            } else {
                return "MongoDB connection failed: Could not retrieve saved document";
            }
        } catch (Exception e) {
            return "MongoDB connection failed: " + e.getMessage();
        }
    }
}
