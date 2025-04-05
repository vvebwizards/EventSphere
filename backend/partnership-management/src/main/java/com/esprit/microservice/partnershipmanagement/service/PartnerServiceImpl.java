package com.esprit.microservice.partnershipmanagement.service;

import com.esprit.microservice.partnershipmanagement.entity.Partner;
import com.esprit.microservice.partnershipmanagement.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;

    @Autowired
    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Override
    public Partner createPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public Partner updatePartner(Long id, Partner partner) {
        return partnerRepository.findById(id)
                .map(existingPartner -> {
                    existingPartner.setName(partner.getName());
                    existingPartner.setEmail(partner.getEmail());
                    existingPartner.setPhoneNumber(partner.getPhoneNumber());
                    existingPartner.setAddress(partner.getAddress());
                    existingPartner.setWebsite(partner.getWebsite());
                    existingPartner.setStatus(partner.getStatus());
                    return partnerRepository.save(existingPartner);
                })
                .orElseThrow(() -> new RuntimeException("Partner not found with id " + id));
    }

    @Override
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }

    @Override
    public Optional<Partner> getPartnerById(Long id) {
        return partnerRepository.findById(id);
    }

    @Override
    public List<Partner> getAllPartners() {
        return partnerRepository.findAll();
    }

    // Advanced search implementation
    @Override
    public List<Partner> searchPartners(String name, String status) {
        if (name != null && status != null) {
            return partnerRepository.findByNameContainingIgnoreCaseAndStatus(name, status);
        } else if (name != null) {
            return partnerRepository.findByNameContainingIgnoreCase(name);
        } else if (status != null) {
            return partnerRepository.findByStatus(status);
        } else {
            return partnerRepository.findAll();
        }
    }
    @Override
    public Map<String, Long> getPartnerStatistics() {
        List<Object[]> results = partnerRepository.countPartnersByStatus();
        Map<String, Long> statistics = new HashMap<>();
        for (Object[] result : results) {
            String status = (String) result[0];
            Long count = (Long) result[1];
            statistics.put(status, count);
        }
        return statistics;
    }

}
