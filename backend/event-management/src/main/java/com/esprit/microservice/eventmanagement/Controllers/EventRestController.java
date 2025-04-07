package com.esprit.microservice.eventmanagement.Controllers;

import com.esprit.microservice.eventmanagement.entities.Event;
import com.esprit.microservice.eventmanagement.service.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/event")
@RestController
@AllArgsConstructor
public class EventRestController {
    IEventService eventService;
    @PostMapping("/add-Event")
    public Event addEvent(@RequestBody Event e){
        return eventService.addEvent(e); }

    @GetMapping("/get-all-Events")
    public List<Event> getEvents() {
        return eventService.getall();
    }

    @GetMapping("/retrieve/{id}")
    public Event retrieveEvent(@PathVariable("id")String id) {
        return eventService.retrieveEvent(id);
    }
    @PutMapping("/update-reclamation")
    public Event update(Event e) {
        return eventService.updateEvent(e);
    }
    @DeleteMapping("/remove-etudiant/{event-id}")
    public void removeEtudiant(@PathVariable("event-id") String id) {
        eventService.removeEvent(id);
    }

}
