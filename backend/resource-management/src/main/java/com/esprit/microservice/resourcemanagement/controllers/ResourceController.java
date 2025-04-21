package com.esprit.microservice.resourcemanagement.controllers;

import com.esprit.microservice.resourcemanagement.dto.BookingRequest;
import com.esprit.microservice.resourcemanagement.dto.BookingRevenueReport;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.dto.SearchResourceDTO;
import com.esprit.microservice.resourcemanagement.entities.Resource;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.services.ResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/resources")
@AllArgsConstructor
public class ResourceController {


    private final ResourceRepository resourceRepository;
    private  ResourceService resourceService;

    @RolesAllowed("user")
    @GetMapping("/getAll")
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceService.getAllResources());
    }

    @RolesAllowed("resource-owner")
    @GetMapping("/my-resources")
    public List<Resource> getAllResourcesByOwnerId(){
        return  resourceService.getAllResourcesByOwnerId();
    }

    @GetMapping("/getOne/{id}")
    @RolesAllowed("resource-owner")
    public ResponseEntity<Resource> getResourceById(@PathVariable UUID id) {

        return ResponseEntity.ok(resourceService.getResourceById(id));
    }
    @GetMapping("/images/{filename}")
    public ResponseEntity<UrlResource> getImage(@PathVariable String filename) throws IOException {
        // Path to the image file
        Path path = Paths.get("src/main/resources/static/uploads/" + filename);
        // Load the resource
        UrlResource resource = new UrlResource(path.toUri());
        // Return ResponseEntity with image content type
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @RolesAllowed("resource-owner")
    @PostMapping("/addOne")
    public ResponseEntity<Resource> createResource(@RequestPart("resource") String resourceJson,
                                                   @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        try {
            System.out.println("Received JSON: " + resourceJson);
            System.out.println("Image file: " + (imageFile != null ? imageFile.getOriginalFilename() : "No image uploaded"));

            // Convert JSON string to Resource object
            ObjectMapper objectMapper = new ObjectMapper();
            Resource resource = objectMapper.readValue(resourceJson, Resource.class);

            Resource saved = resourceService.createResource(resource, imageFile);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/updateOne/{id}")
    @RolesAllowed("resource-owner")
    public ResponseEntity<Resource> updateResource(@PathVariable UUID id, @RequestBody Resource resource) {
        return ResponseEntity.ok(resourceService.updateResource(id, resource));
    }

    @DeleteMapping("Delete/{id}")
    @RolesAllowed("resource-owner")
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
