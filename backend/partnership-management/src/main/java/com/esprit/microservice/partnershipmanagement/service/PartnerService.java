package com.esprit.microservice.partnershipmanagement.service;

import com.esprit.microservice.partnershipmanagement.entity.Partner;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PartnerService {
    Partner createPartner(Partner partner);
    Partner updatePartner(Long id, Partner partner);
    void deletePartner(Long id);
    Optional<Partner> getPartnerById(Long id);
    List<Partner> getAllPartners();
    List<Partner> searchPartners(String name, String status);
    Map<String, Long> getPartnerStatistics();

}
