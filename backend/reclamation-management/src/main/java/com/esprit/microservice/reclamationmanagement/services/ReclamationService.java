package com.esprit.microservice.reclamationmanagement.services;

import com.esprit.microservice.reclamationmanagement.entities.Reclamation;
import com.esprit.microservice.reclamationmanagement.repositories.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReclamationService implements IReclamationService{
    ReclamationRepository reclamationRepository;

    public Reclamation addReclamation(Reclamation r) {
        return reclamationRepository.save(r);
    }

    public List<Reclamation> getReclamations() {
        return reclamationRepository.findAll();
    }
}
