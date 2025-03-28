package com.esprit.microservice.eventmanagement;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestRepository extends MongoRepository<TestDocument, String> {

}
