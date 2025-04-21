package com.esprit.microservice.eventmanagement.service;

import com.esprit.microservice.eventmanagement.Configuration.UserClient;
import com.esprit.microservice.eventmanagement.entities.Event;
import com.esprit.microservice.eventmanagement.repositories.EventRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document; // ✅ Classe correcte pour générer un PDF

import java.io.IOException;
import java.util.List;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.esprit.microservice.eventmanagement.dto.UserDTO;
import org.springframework.http.ResponseEntity;

@Service
@AllArgsConstructor
public class EventService implements  IEventService{
    private EventRepository eventRepository;
    private final UserClient userClient;
    public Event addEvent (Event e )
    {    ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        if (currentUser == null || currentUser.getId() == null) {
            throw new RuntimeException("Unauthorized: Cannot create event without user ID.");
        }

        e.setOwnerId(currentUser.getId());
        return  eventRepository.save(e);   }

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

    @Override
    public void generatePdf(String id, HttpServletResponse response) throws IOException {
        Event event = retrieveEvent(id); // ou eventRepository.findById(id)...

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=event_" + id + ".pdf");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Event Details"));
        document.add(new Paragraph("Title: " + event.getTitle()));
        document.add(new Paragraph("Description: " + event.getDescription()));
        document.add(new Paragraph("Date: " + event.getDate()));
        document.add(new Paragraph("Location: " + event.getLocation()));

        document.close();
    }
    @Override
    public List<Event> getAllEventsByOwnerId() {
        ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        if (currentUser == null || currentUser.getId() == null) {
            throw new RuntimeException("Unauthorized: Cannot fetch resources without user ID.");
        }

        return eventRepository.findResourceByOwnerId(currentUser.getId());
    }

}
