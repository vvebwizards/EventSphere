package com.esprit.microservice.eventmanagement.Controllers;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import jakarta.servlet.http.HttpServletResponse;


import com.esprit.microservice.eventmanagement.entities.Event;
import com.esprit.microservice.eventmanagement.service.IEventService;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequestMapping("/event")
@RestController
@AllArgsConstructor
@Tag(name = "Event Management", description = "Endpoints for managing events")
public class EventRestController {

    IEventService eventService;

    @PostMapping("/add-Event")
    @Operation(summary = "Add new event", description = "Create and save a new event")
    public Event addEvent(@RequestBody Event e) {
        return eventService.addEvent(e);
    }

    @GetMapping("/get-all-Events")
    @Operation(summary = "Get all events", description = "Retrieve the list of all events")
    public List<Event> getEvents() {
        return eventService.getall();
    }

    @GetMapping("/retrieve/{id}")
    @Operation(summary = "Retrieve event by ID", description = "Get a single event using its ID")
    public Event retrieveEvent(
            @Parameter(description = "ID of the event to retrieve") @PathVariable("id") String id) {
        return eventService.retrieveEvent(id);
    }

    @PutMapping("/update-reclamation")
    @Operation(summary = "Update event", description = "Update the details of an existing event")
    public Event update(@RequestBody Event e) {
        return eventService.updateEvent(e);
    }


    @DeleteMapping("/remove-etudiant/{event-id}")
    @Operation(summary = "Delete event", description = "Remove an event using its ID")
    public void removeEtudiant(
            @Parameter(description = "ID of the event to delete") @PathVariable("event-id") String id) {
        eventService.removeEvent(id);
    }

    @GetMapping("/pdf/{id}")
    public void generatePdf(@PathVariable String id, HttpServletResponse response) throws IOException {
        eventService.generatePdf(id, response);
    }


}
