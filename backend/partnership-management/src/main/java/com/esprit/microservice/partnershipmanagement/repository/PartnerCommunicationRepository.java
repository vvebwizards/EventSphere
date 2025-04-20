package com.esprit.microservice.partnershipmanagement.repository;

import com.esprit.microservice.partnershipmanagement.entity.PartnerCommunication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerCommunicationRepository extends JpaRepository<PartnerCommunication, Long> {
    List<PartnerCommunication> findByPartnerId(Long partnerId);
}
