package com.esprit.microservice.eventmanagement.repositories;

import com.esprit.microservice.eventmanagement.entities.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findResourceByOwnerId(String ownerId);
    Event findResourceByOwnerIdAndId(String ownerId,String id);
}

