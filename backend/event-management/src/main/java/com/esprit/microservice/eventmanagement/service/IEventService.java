package com.esprit.microservice.eventmanagement.service;

import com.esprit.microservice.eventmanagement.entities.Event;

import java.util.List;

public interface IEventService {

    Event  addEvent (Event e );
    List<Event> getall ();
    Event updateEvent(Event e);
    Event retrieveEvent(String id);
    void removeEvent(String id);

}
