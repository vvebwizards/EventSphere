package com.esprit.microservice.reclamationmanagement.controllers;

import com.esprit.microservice.reclamationmanagement.entities.Reclamation;
import com.esprit.microservice.reclamationmanagement.services.IReclamationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reclamation")
public class ReclamationRestController {
    IReclamationService reclamationService;

    @PostMapping("/add-reclamation")
    public Reclamation addReclamation(@RequestBody Reclamation r) {
        return reclamationService.addReclamation(r);
    }

    @GetMapping("/get-all-reclamations")
    public List<Reclamation> getReclamations() {
        return reclamationService.getReclamations();
    }
}
