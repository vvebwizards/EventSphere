package com.esprit.microservice.eventmanagement.service;

import com.esprit.microservice.eventmanagement.entities.Event;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface IEventService {

    Event  addEvent (Event e );
    List<Event> getall ();
    Event updateEvent(Event e);
    Event retrieveEvent(String id);
    void removeEvent(String id);
    void generatePdf(String id, HttpServletResponse response) throws IOException;


}
