package com.esprit.microservice.eventmanagement.service;

import com.esprit.microservice.eventmanagement.entities.Event;
import com.esprit.microservice.eventmanagement.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService implements  IEventService{
    private EventRepository eventRepository;
    public Event addEvent (Event e ) {return  eventRepository.save(e);   }

    @Override
    public List<Event> getall() {
        return eventRepository.findAll();
    }
    @Override
    public Event updateEvent(Event e) {
        return eventRepository.save(e);
    }

    @Override
    public Event retrieveEvent(String id) {
        return eventRepository.findById(id).orElse(null);
    }

    @Override
    public void removeEvent(String id) {
        eventRepository.deleteById(id);
    }

}
