package com.esprit.microservice.reclamationmanagement.services;

import com.esprit.microservice.reclamationmanagement.entities.Reclamation;

import java.util.List;

public interface IReclamationService {
    Reclamation addReclamation(Reclamation r);
    List<Reclamation> getReclamations();
}
