package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.dto.BookingRevenueReport;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.Resource;
import com.esprit.microservice.resourcemanagement.entities.ResourceType;
import com.esprit.microservice.resourcemanagement.entities.RessourceBooking;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.repositories.RessourceBookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
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
        resource.setLocation(resource.getLocation());
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
        for (Resource resource : resources) {

            Double resourceBookedHours = bookingRepository.getRessourceTotalHoursBooked(startDate,endDate,resource.getId());
            Double resourceBookingPourcentage = this.getResourceBookingPourcentage(resource,startDate,endDate);
            Double resourceBookingRevenue = resourceBookedHours * resource.getCostPerHour() ;

            BookingRevenueReport bookingRevenueReport = new BookingRevenueReport(resource.getName(),resourceBookingPourcentage,resourceBookingRevenue);
            revenueReports.add(bookingRevenueReport);
        }

        return revenueReports;
    }

    private boolean isWeekend(LocalDateTime date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek == 6 || dayOfWeek == 7;
    }
    private double getTimeFactor(Resource resource) {

        if (isWeekend(resource.getLastBookedDate())) {
            return 0.2;
        }
        return 0.0;
    }

    public Double getResourceBookingPourcentage(Resource resource , LocalDateTime startDate , LocalDateTime endDate) {
        Double resourceBookedHours = bookingRepository.getRessourceTotalHoursBooked(startDate,endDate,resource.getId());
        Double totalBookingHours = bookingRepository.getBookingTotalHoursBooked(startDate,endDate);
        Double resourceBookingPourcentage = (resourceBookedHours * 100)/totalBookingHours;
        return  resourceBookingPourcentage;
    }
    private double getUtilizationFactor(Resource resource) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth())
                .withHour(23).withMinute(59).withSecond(59).withNano(999999999);

        double bookingPourcentage = getResourceBookingPourcentage(resource, startOfMonth, endOfMonth);

        if (bookingPourcentage > 0.6) {
            return 0.15;
        }
        return 0.0;
    }
    public double calculateDynamicPrice(Resource resource) {
        double timeFactor = getTimeFactor(resource);
        double utilizationFactor = getUtilizationFactor(resource);
        double dynamicPrice = resource.getCostPerHour() * (1 + timeFactor) * (1 + utilizationFactor);
        double maxPrice = resource.getCostPerHour() * 2;
        return Math.min(dynamicPrice, maxPrice);
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void updateWeeklyDynamicPrices() {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            Double newPrice = calculateDynamicPrice(resource);
            resource.setDynamicPricePerHour(newPrice);
            resourceRepository.save(resource);
        }
        log.info("[Scheduler] Weekly dynamic prices updated.");
    }
    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateMonthlyDynamicPrices() {
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {
            double newPrice = calculateDynamicPrice(resource);
            resource.setDynamicPricePerHour(newPrice);
            resourceRepository.save(resource);
        }
        log.info("[Scheduler] Monthly dynamic prices updated.");
    }

    public List<Resource> searchResources(String location, ResourceType type, String priceRange, boolean availability) {
        List<Resource> resources = resourceRepository.findAll();
        if (location != null && !location.isEmpty()) {
            resources = resources.stream()
                    .filter(resource -> resource.getLocation().contains(location))
                    .collect(Collectors.toList());
        }
        if (type != null) {
            resources = resources.stream()
                    .filter(resource -> resource.getType().equals(type))
                    .collect(Collectors.toList());
        }
        if (priceRange != null && !priceRange.isEmpty()) {
            String[] priceBounds = priceRange.split("-");
            double minPrice = Double.parseDouble(priceBounds[0]);
            double maxPrice = Double.parseDouble(priceBounds[1]);

            resources = resources.stream()
                    .filter(resource -> resource.getCostPerHour() >= minPrice && resource.getCostPerHour() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (availability) {
            resources = resources.stream()
                    .filter(Resource::isAvailable)
                    .collect(Collectors.toList());
        }
        return resources;
    }
}
