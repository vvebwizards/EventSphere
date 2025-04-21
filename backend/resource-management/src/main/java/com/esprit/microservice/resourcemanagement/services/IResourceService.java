package com.esprit.microservice.resourcemanagement.services;

import com.esprit.microservice.resourcemanagement.dto.BookingRevenueReport;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.entities.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface IResourceService {

    public List<Resource> getAllResources();
    public Resource getResourceById(UUID id);
    public Resource createResource(Resource resource, MultipartFile imageFile);
    public Resource updateResource(UUID id, Resource resourceDetails);
    public void deleteResource(UUID id);
    public  List<ResourceUtilizationReport> getResourceUtilizationReport(LocalDateTime startDate, LocalDateTime endDate);
    public  List<BookingRevenueReport> getRessourceRevenueAndBookingPourcentage(LocalDateTime startDate, LocalDateTime endDate);
    public List<Resource> getAllResourcesByOwnerId();
}
