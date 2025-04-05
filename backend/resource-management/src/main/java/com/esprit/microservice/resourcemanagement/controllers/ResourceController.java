package com.esprit.microservice.resourcemanagement.controllers;

import com.esprit.microservice.resourcemanagement.dto.BookingRequest;
import com.esprit.microservice.resourcemanagement.dto.BookingRevenueReport;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.dto.SearchResourceDTO;
import com.esprit.microservice.resourcemanagement.entities.Resource;
import com.esprit.microservice.resourcemanagement.entities.ResourceType;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.services.ResourceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/resources")
@AllArgsConstructor
public class ResourceController {
    private final ResourceRepository resourceRepository;
    private  ResourceService resourceService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<Resource> getResourceById(@PathVariable UUID id) {
        return ResponseEntity.ok(resourceService.getResourceById(id));
    }

    @PostMapping("/addOne")
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {
        return ResponseEntity.ok(resourceService.createResource(resource));
    }

    @PutMapping("updateOne/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable UUID id, @RequestBody Resource resource) {
        return ResponseEntity.ok(resourceService.updateResource(id, resource));
    }

    @DeleteMapping("Delete/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID id) {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/utilizationReport")
    public ResponseEntity<List<ResourceUtilizationReport>> postUtilizationReport(
            @Valid @RequestBody BookingRequest request
    ) {
        return ResponseEntity.ok(
                resourceService.getResourceUtilizationReport(
                        request.getStartTime(),
                        request.getEndTime()
                )
        );
    }
    @PostMapping("/BookingRevenueReport")
    public  List<BookingRevenueReport> getRessourceRevenueAndBookingPourcentage(@RequestBody BookingRequest bookingRequest)
    {
        return resourceService.getRessourceRevenueAndBookingPourcentage(bookingRequest.getStartTime(),bookingRequest.getEndTime());
    }

    @PostMapping(value = "/search")
    public List<Resource> searchResources(@RequestBody SearchResourceDTO searchResourceDTO) {
        return resourceService.searchResources(searchResourceDTO);
    }


}
