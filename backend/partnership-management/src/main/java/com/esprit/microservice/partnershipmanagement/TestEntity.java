package com.esprit.microservice.partnershipmanagement;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestEntity {
    @Id
    private Long id;
    private String name;

    // Constructors
    public TestEntity() {}
    public TestEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
