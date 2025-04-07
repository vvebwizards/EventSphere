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

    @Override
    public Reclamation addReclamation(Reclamation r) {
        return reclamationRepository.save(r);
    }

    @Override
    public List<Reclamation> getReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Reclamation updateReclamation(Reclamation r) {
        return reclamationRepository.save(r);
    }

    @Override
    public void removeReclamation(Long idReclamation) {
        reclamationRepository.deleteById(idReclamation);
    }

    @Override
    public Reclamation retrieveReclamation(Long idReclamation) {
        return reclamationRepository.findById(idReclamation).orElse(null);
    }
}
