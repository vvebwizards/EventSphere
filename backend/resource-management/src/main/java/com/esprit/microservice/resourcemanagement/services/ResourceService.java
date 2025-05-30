package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.config.UserClient;
import com.esprit.microservice.resourcemanagement.dto.BookingRevenueReport;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.dto.SearchResourceDTO;
import com.esprit.microservice.resourcemanagement.dto.UserDTO;
import com.esprit.microservice.resourcemanagement.entities.BookingStatus;
import com.esprit.microservice.resourcemanagement.entities.Resource;
import com.esprit.microservice.resourcemanagement.entities.ResourceType;
import com.esprit.microservice.resourcemanagement.entities.RessourceBooking;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.repositories.RessourceBookingRepository;

import com.esprit.microservice.resourcemanagement.uploads.FileUploadUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ResourceService implements IResourceService {

    private ResourceRepository resourceRepository;

    private RessourceBookingRepository bookingRepository;
    private final UserClient userClient;

    // user can see all the ressources
    @Override
    public List<Resource> getAllResources() {
        return resourceRepository.findAll();
    }
    //resource owner can retrieve only his resources
    @Override
    public List<Resource> getAllResourcesByOwnerId() {
        ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        if (currentUser == null || currentUser.getId() == null) {
            throw new RuntimeException("Unauthorized: Cannot fetch resources without user ID.");
        }

        return resourceRepository.findResourceByOwnerId(currentUser.getId());
    }


    @Override
    public Resource getResourceById(UUID id) {
        return resourceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resource not found with ID: " + id));
    }


    //add the resource by owner id
    @Override
    @Transactional
    public Resource createResource(Resource resource, MultipartFile imageFile) {
        ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        if (currentUser == null || currentUser.getId() == null) {
            throw new RuntimeException("Unauthorized: Cannot create resource without user ID.");
        }

        resource.setOwnerId(currentUser.getId());

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                FileUploadUtil.saveFile(fileName, imageFile.getBytes());

                resource.setImagePath("http://localhost:8087/resource-api/resources/images/"+ fileName); // Set the path to the database
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        return resourceRepository.save(resource);
    }


    //only the resource owner can update the resource
    @Override
    @Transactional
    public Resource updateResource(UUID id, Resource resourceDetails, MultipartFile imageFile) {
        ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        if (currentUser == null || currentUser.getId() == null) {
            throw new RuntimeException("User not found or unauthorized");
        }

        Resource resource = resourceRepository.findResourceByOwnerIdAndId(currentUser.getId(), id);
        if (resource == null) {
            throw new EntityNotFoundException("Resource not found for this user");
        }

        // Update basic fields
        resource.setName(resourceDetails.getName());
        resource.setType(resourceDetails.getType());
        resource.setDescription(resourceDetails.getDescription());
        resource.setAvailable(resourceDetails.isAvailable());
        resource.setCostPerHour(resourceDetails.getCostPerHour());
        resource.setLocation(resourceDetails.getLocation());
        resource.setLastBookedDate(resourceDetails.getLastBookedDate());

        // Handle image update (same as createResource)
        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                // Delete old image if it exists
                if (resource.getImagePath() != null) {
                    deleteOldImage(resource.getImagePath());
                }

                // Save new image
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                FileUploadUtil.saveFile(fileName, imageFile.getBytes());
                resource.setImagePath("http://localhost:8087/resource-api/resources/images/" + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }

        return resourceRepository.save(resource);
    }

    private void deleteOldImage(String imagePath) {
        try {
            String filename = imagePath.substring(imagePath.lastIndexOf('/') + 1);
            Path filePath = Paths.get("src/main/resources/static/uploads", filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            System.err.println("Failed to delete old image: " + e.getMessage());
        }
    }

    //only the owner can delete the resource
    @Override
    public void deleteResource(UUID id) {
        ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        Resource resource = resourceRepository.findResourceByOwnerIdAndId(currentUser.getId(),id);
        if (resource== null) {
            throw new EntityNotFoundException("Resource not found ");
        }
        List<RessourceBooking> ressourceBookings = bookingRepository.findRessourceBookingByResourceIdAndStatus(id, BookingStatus.CONFIRMED);
        if (!ressourceBookings.isEmpty()) {
            throw new IllegalStateException("Cannot delete this resource because it is currently booked.");
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
        List<BookingRevenueReport> revenueReports = new ArrayList<>();
        List<Resource> resources = resourceRepository.findAll();
        for (Resource resource : resources) {

            Double resourceBookedHours = bookingRepository.getRessourceTotalHoursBooked(startDate, endDate, resource.getId());
            Double resourceBookingPourcentage = this.getResourceBookingPourcentage(resource, startDate, endDate);
            Double resourceBookingRevenue = resourceBookedHours * resource.getCostPerHour();

            BookingRevenueReport bookingRevenueReport = new BookingRevenueReport(resource.getName(), resourceBookingPourcentage, resourceBookingRevenue);
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

    public Double getResourceBookingPourcentage(Resource resource, LocalDateTime startDate, LocalDateTime endDate) {
        Double resourceBookedHours = bookingRepository.getRessourceTotalHoursBooked(startDate, endDate, resource.getId());
        Double totalBookingHours = bookingRepository.getBookingTotalHoursBooked(startDate, endDate);
        Double resourceBookingPourcentage = (resourceBookedHours * 100) / totalBookingHours;
        return resourceBookingPourcentage;
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

    public List<Resource> searchResources(SearchResourceDTO searchResourceDTO) {
        List<Resource> resources = resourceRepository.findAll();

       if (searchResourceDTO.getLocation() != null && !searchResourceDTO.getLocation().isEmpty()) {
            resources = resources.stream()
                    .filter(resource -> resource.getLocation().contains(searchResourceDTO.getLocation()))
                    .collect(Collectors.toList());
        }

        if (searchResourceDTO.getType() != null) {
            resources = resources.stream()
                    .filter(resource -> resource.getType().equals(searchResourceDTO.getType()))
                    .collect(Collectors.toList());
        }

        if (searchResourceDTO.getPriceRange() != null && !searchResourceDTO.getPriceRange().isEmpty()) {
            String[] priceBounds = searchResourceDTO.getPriceRange().split("-");
            double minPrice = Double.parseDouble(priceBounds[0]);
            double maxPrice = Double.parseDouble(priceBounds[1]);

            resources = resources.stream()
                    .filter(resource -> {
                        double costPerHour = (double) resource.getCostPerHour();
                        return costPerHour >= minPrice && costPerHour <= maxPrice;
                    })
                    .collect(Collectors.toList());
        }

        if (Boolean.TRUE.equals(searchResourceDTO.getAvailability())) {
            {
                resources = resources.stream()
                        .filter(Resource::isAvailable)
                        .collect(Collectors.toList());
            }


        }
        return resources;
    }
}

