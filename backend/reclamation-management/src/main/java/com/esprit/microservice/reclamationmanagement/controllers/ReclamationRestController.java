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

    @PutMapping("/update-reclamation")
    public Reclamation updateReclamation(Reclamation r) {
        return reclamationService.updateReclamation(r);
    }

    @DeleteMapping("/remove-reclamation/{reclamation-id}")
    public void removeReclamation(@PathVariable("reclamation-id") Long idReclamation) {
        reclamationService.removeReclamation(idReclamation);
    }

    @GetMapping("/get-reclamation/{reclamation-id}")
    public Reclamation getReclamationById(@PathVariable("reclamation-id") Long idReclamation) {
        return reclamationService.retrieveReclamation(idReclamation);
    }
}
