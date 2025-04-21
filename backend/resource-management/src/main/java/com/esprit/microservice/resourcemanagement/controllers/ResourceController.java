package com.esprit.microservice.resourcemanagement.controllers;

import com.esprit.microservice.resourcemanagement.dto.BookingRequest;
import com.esprit.microservice.resourcemanagement.dto.BookingRevenueReport;
import com.esprit.microservice.resourcemanagement.dto.ResourceUtilizationReport;
import com.esprit.microservice.resourcemanagement.dto.SearchResourceDTO;
import com.esprit.microservice.resourcemanagement.entities.Resource;
import com.esprit.microservice.resourcemanagement.entities.ResourceType;
import com.esprit.microservice.resourcemanagement.repositories.ResourceRepository;
import com.esprit.microservice.resourcemanagement.services.ResourceService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
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
    @RolesAllowed("resource-owner")
    @PostMapping("/addOne")
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource) {

        return ResponseEntity.ok(resourceService.createResource(resource));
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
