package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.*;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.repositories.RessourceBookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RessourceBookingService implements IResourceBookingService{

    private RessourceBookingRepository bookingRepository;
    private ResourceRepository resourceRepository;
    private final EmailService emailService;
    @Override
    public RessourceBooking createBooking(UUID resourceId, LocalDateTime startTime, LocalDateTime endTime, String bookedBy) {
        // Fetch the resource by ID
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null || !resource.isAvailable()) {
            throw new RuntimeException("Resource not available");
        }

        // Check for overlapping bookings
        List<RessourceBooking> existingBookings = bookingRepository.findByResourceIdAndStartTimeBeforeAndEndTimeAfter(resourceId, endTime, startTime);
        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("Time slot already booked");
        }

        RessourceBooking booking = new RessourceBooking();
        booking.setResource(resource);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setBookedBy(bookedBy);

        emailService.sendBookingConfirmation(bookedBy, booking.getResource().getName(), startTime.toString(), endTime.toString());

        return bookingRepository.save(booking);
    }
    @Override
    public List<RessourceBooking> getBookingsByResourceId(UUID resourceId) {
        return bookingRepository.findByResourceIdAndStartTimeBetween(resourceId, LocalDateTime.now(), LocalDateTime.now());
    }
    @Override
    public void cancelBooking(UUID bookingId) {
        bookingRepository.deleteById(bookingId);
    }

}
