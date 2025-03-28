package com.esprit.microservice.eventmanagement;

import org.springframework.data.annotation.Id;

public class TestDocument {
    @Id
    private String id;
    private String name;

    // Constructors
    public TestDocument() {}
    public TestDocument(String name) {
        this.name = name;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
