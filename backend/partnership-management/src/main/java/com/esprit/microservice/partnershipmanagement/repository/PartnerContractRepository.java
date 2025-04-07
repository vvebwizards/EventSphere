package com.esprit.microservice.partnershipmanagement.repository;

import com.esprit.microservice.partnershipmanagement.entity.PartnerContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerContractRepository extends JpaRepository<PartnerContract, Long> {
    List<PartnerContract> findByPartnerId(Long partnerId);
}
