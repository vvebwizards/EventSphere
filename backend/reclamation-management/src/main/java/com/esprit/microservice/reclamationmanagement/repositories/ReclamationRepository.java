package com.esprit.microservice.reclamationmanagement.repositories;

import com.esprit.microservice.reclamationmanagement.entities.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
}
