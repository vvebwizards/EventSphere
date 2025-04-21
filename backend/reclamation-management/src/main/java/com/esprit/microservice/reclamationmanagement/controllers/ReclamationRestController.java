package com.esprit.microservice.reclamationmanagement.controllers;

import com.esprit.microservice.reclamationmanagement.entities.Reclamation;
import com.esprit.microservice.reclamationmanagement.services.IReclamationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reclamation")
@Tag(name = "Reclamation Management", description = "APIs for managing reclamations")
public class ReclamationRestController {
    IReclamationService reclamationService;

    @PostMapping("/add-reclamation")
    @Operation(summary = "Add a new reclamation", description = "Creates a new reclamation and returns the created entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reclamation created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid reclamation data provided")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public Reclamation addReclamation(@RequestBody Reclamation r) {
        return reclamationService.addReclamation(r);
    }

    @RolesAllowed("user")
    @GetMapping("/get-all-reclamations")
    @Operation(summary = "Retrieve all reclamations", description = "Returns a list of all reclamations.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of reclamations retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No reclamations found")
    })
    public List<Reclamation> getReclamations() {
        return reclamationService.getReclamations();
    }

    @PutMapping("/update-reclamation")
    @Operation(summary = "Update an existing reclamation", description = "Updates the details of an existing reclamation and returns the updated entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamation updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid reclamation data provided"),
            @ApiResponse(responseCode = "404", description = "Reclamation not found")
    })
    public Reclamation updateReclamation(@RequestBody Reclamation r) {
        return reclamationService.updateReclamation(r);
    }

    @DeleteMapping("/remove-reclamation/{reclamation-id}")
    @Operation(summary = "Delete a reclamation", description = "Deletes a reclamation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Reclamation deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Reclamation not found")
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeReclamation(
            @Parameter(description = "ID of the reclamation to delete", required = true)
            @PathVariable("reclamation-id") Long idReclamation) {
        reclamationService.removeReclamation(idReclamation);
    }

    @GetMapping("/get-reclamation/{reclamation-id}")
    @Operation(summary = "Retrieve a reclamation by ID", description = "Returns a reclamation by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reclamation retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Reclamation not found")
    })
    public Reclamation getReclamationById(
            @Parameter(description = "ID of the reclamation to retrieve", required = true)
            @PathVariable("reclamation-id") Long idReclamation) {
        return reclamationService.retrieveReclamation(idReclamation);
    }
}