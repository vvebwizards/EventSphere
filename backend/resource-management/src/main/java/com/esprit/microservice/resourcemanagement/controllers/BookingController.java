package com.esprit.microservice.resourcemanagement.controllers;

import com.esprit.microservice.resourcemanagement.dto.BookingRequest;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.RessourceBooking;
import com.esprit.microservice.resourcemanagement.services.RessourceBookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resource/bookings")
@AllArgsConstructor
public class BookingController {

    private RessourceBookingService bookingService ;


    @PostMapping("/addOne/{resourceId}/{bookedBy}")
    public RessourceBooking createBooking(@PathVariable UUID resourceId,
                                          @RequestBody BookingRequest bookingRequest,
                                          @PathVariable String bookedBy) {
        RessourceBooking booking = bookingService.createBooking(resourceId, bookingRequest.getStartTime(), bookingRequest.getEndTime(), bookedBy);
        return booking;
    }

    @GetMapping("/resource/{resourceId}")
    public List<RessourceBooking> getBookings(@PathVariable UUID resourceId) {
        return bookingService.getBookingsByResourceId(resourceId);
    }

    @DeleteMapping("/cancel/{id}")
    public void cancelBooking(@PathVariable UUID id) {
        bookingService.cancelBooking(id);
    }

    @PostMapping("/utilizationReport")
    public ResponseEntity<List<ResourceUtilizationReport>> postUtilizationReport(
            @Valid @RequestBody BookingRequest request
    ) {
        return ResponseEntity.ok(
                bookingService.getResourceUtilizationReport(
                        request.getStartTime(),
                        request.getEndTime()
                )
        );
    }
}
