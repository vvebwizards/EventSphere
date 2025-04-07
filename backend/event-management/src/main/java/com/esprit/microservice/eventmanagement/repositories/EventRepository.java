package com.esprit.microservice.eventmanagement.repositories;

import com.esprit.microservice.eventmanagement.entities.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {


}
