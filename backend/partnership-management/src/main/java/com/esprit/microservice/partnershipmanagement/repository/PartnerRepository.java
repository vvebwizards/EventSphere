package com.esprit.microservice.partnershipmanagement.repository;

import com.esprit.microservice.partnershipmanagement.entity.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    List<Partner> findByNameContainingIgnoreCase(String name);
    List<Partner> findByStatus(String status);
    List<Partner> findByNameContainingIgnoreCaseAndStatus(String name, String status);
    @Query("SELECT p.status, COUNT(p) FROM Partner p GROUP BY p.status")
    List<Object[]> countPartnersByStatus();

}
