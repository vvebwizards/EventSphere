package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.dto.BookingRevenueReport;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.Resource;
import com.esprit.microservice.resourcemanagement.entities.RessourceBooking;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.repositories.RessourceBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ResourceService implements IResourceService{

    private ResourceRepository resourceRepository;
    private RessourceBookingRepository bookingRepository;
    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }

    @Override
    public Resource getResourceById(UUID id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with ID: " + id));
    }

    @Override
    @Transactional
    public Resource createResource(Resource resource) {
        return resourceRepository.save(resource);
    }

    @Override
    public Resource updateResource(UUID id, Resource resourceDetails) {
        Resource resource = getResourceById(id);
        resource.setName(resourceDetails.getName());
        resource.setType(resourceDetails.getType());
        resource.setDescription(resourceDetails.getDescription());
        resource.setAvailable(resourceDetails.isAvailable());
        resource.setCostPerHour(resourceDetails.getCostPerHour());
        resource.setLocation(resourceDetails.getLocation());
        resource.setLastBookedDate(resourceDetails.getLastBookedDate());
        return resourceRepository.save(resource);
    }

    @Override
    public void deleteResource(UUID id) {
        if (!resourceRepository.existsById(id)) {
            throw new EntityNotFoundException("Resource not found with ID: " + id);
        }
        resourceRepository.deleteById(id);

    }
    @Override
    public List<ResourceUtilizationReport> getResourceUtilizationReport(
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must precede end date");
        }
        if (ChronoUnit.DAYS.between(startDate, endDate) > 365) {
            throw new IllegalArgumentException("Date range cannot exceed 1 year");
        }
        return bookingRepository.getResourceUtilizationReport(startDate, endDate);
    }

    @Override
    public List<BookingRevenueReport> getRessourceRevenueAndBookingPourcentage(LocalDateTime startDate, LocalDateTime endDate) {
        List<BookingRevenueReport> revenueReports = new ArrayList<>() ;
        List<Resource> resources = resourceRepository.findAll();
        Double totalBookingHours = bookingRepository.getBookingTotalHoursBooked(startDate,endDate);
        for (Resource resource : resources) {

            Double resourceBookedHours = bookingRepository.getRessourceTotalHoursBooked(startDate,endDate,resource.getId());
            Double resourceBookingPourcentage = (resourceBookedHours * 100)/totalBookingHours;
            Double resourceBookingRevenue = resourceBookedHours * resource.getCostPerHour() ;

            BookingRevenueReport bookingRevenueReport = new BookingRevenueReport(resource.getName(),resourceBookingPourcentage,resourceBookingRevenue);
            revenueReports.add(bookingRevenueReport);
        }

        return revenueReports;
    }
}
