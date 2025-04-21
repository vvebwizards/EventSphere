package com.esprit.microservice.reclamationmanagement.services;

import com.esprit.microservice.reclamationmanagement.configuration.UserClient;
import com.esprit.microservice.reclamationmanagement.dto.UserDTO;
import com.esprit.microservice.reclamationmanagement.entities.Reclamation;
import com.esprit.microservice.reclamationmanagement.repositories.ReclamationRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReclamationService implements IReclamationService{
    ReclamationRepository reclamationRepository;
    private final UserClient userClient;

    @Override
    public Reclamation addReclamation(Reclamation r) {
        ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        if (currentUser == null || currentUser.getId() == null) {
            throw new RuntimeException("Unauthorized: Cannot create resource without user ID.");
        }

        r.setOwnerId(currentUser.getId());

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

    @Override
    public List<Reclamation> getAllReclamationsByOwnerId() {
        ResponseEntity<UserDTO> userResponse = userClient.getUserById();
        UserDTO currentUser = userResponse.getBody();

        if (currentUser == null || currentUser.getId() == null) {
            throw new RuntimeException("Unauthorized: Cannot fetch resources without user ID.");
        }

        return reclamationRepository.findResourceByOwnerId(currentUser.getId());
    }
}
