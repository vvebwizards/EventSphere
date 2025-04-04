package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.RessourceBooking;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public interface IResourceBookingService {

    public RessourceBooking createBooking(UUID resourceId, LocalDateTime startTime, LocalDateTime endTime, String bookedBy);
    public List<RessourceBooking> getBookingsByResourceId(UUID resourceId);
    public void cancelBooking(UUID bookingId);
}
