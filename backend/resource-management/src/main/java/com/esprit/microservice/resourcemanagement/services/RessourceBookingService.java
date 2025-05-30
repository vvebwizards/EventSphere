package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.*;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.repositories.RessourceBookingRepository;
import jakarta.persistence.EntityNotFoundException;
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

        if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
            throw new RuntimeException("Start time must be before end time.");
        }

        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        if (resource == null || !resource.isAvailable()) {
            throw new RuntimeException("Resource not available");
        }


        List<RessourceBooking> existingBookings = bookingRepository.findByResourceIdAndStartTimeBeforeAndEndTimeAfter(resourceId, endTime, startTime);
        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("Time slot already booked");
        }

        RessourceBooking booking = new RessourceBooking();
        booking.setResource(resource);
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setBookedBy(bookedBy);
        resource.setLastBookedDate(startTime);
        resourceRepository.save(resource);
        emailService.sendBookingConfirmation(bookedBy, booking.getResource().getName(), startTime.toString(), endTime.toString());

        return bookingRepository.save(booking);
    }
    @Override
    public List<RessourceBooking> getBookingsByResourceId(UUID resourceId) {
        return bookingRepository.findRessourceBookingByResourceId(resourceId);
    }
    @Override
    public void cancelBooking(UUID bookingId) {
        RessourceBooking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        booking.setStatus(BookingStatus.CANCELLED);

        if (!isCancellationEligible(booking)) {

            Double cancellationFee = calculateCancellationFee(booking);
            booking.setCancelationFee(cancellationFee);
        } else {
            booking.setCancelationFee(0.0);
        }

        bookingRepository.save(booking);
    }
    private boolean isCancellationEligible(RessourceBooking booking) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime bookingStartTime = booking.getStartTime();
        long hoursBetween = ChronoUnit.HOURS.between(now, bookingStartTime);
        return hoursBetween >= 24;
    }
    private Double calculateCancellationFee(RessourceBooking booking) {
        long hoursBooked = ChronoUnit.HOURS.between(booking.getStartTime(), booking.getEndTime());
        Double totalCost = Double.valueOf(hoursBooked * booking.getResource().getCostPerHour());
        return totalCost*0.3;
    }


}


