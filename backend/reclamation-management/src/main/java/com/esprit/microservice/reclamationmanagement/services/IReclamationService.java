package com.esprit.microservice.reclamationmanagement.services;

import com.esprit.microservice.reclamationmanagement.entities.Reclamation;

import java.util.List;

public interface IReclamationService {
    Reclamation addReclamation(Reclamation r);
    List<Reclamation> getReclamations();
    Reclamation updateReclamation(Reclamation r);
    void removeReclamation(Long idReclamation);
    Reclamation retrieveReclamation(Long idReclamation);
    List<Reclamation> getAllReclamationsByOwnerId();
}
