package com.esprit.microservice.partnershipmanagement.service;

import com.esprit.microservice.partnershipmanagement.entity.PartnerContract;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PartnerContractService {
    PartnerContract uploadContract(Long partnerId, MultipartFile file, String contractType, String expirationDate);
    PartnerContract verifyContract(Long partnerId, Long contractId, String verifiedBy);
    List<PartnerContract> getContractsByPartner(Long partnerId);
}
