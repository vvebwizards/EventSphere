package com.esprit.microservice.partnershipmanagement.service;

import com.esprit.microservice.partnershipmanagement.entity.PartnerContract;
import com.esprit.microservice.partnershipmanagement.repository.PartnerContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartnerContractServiceImpl implements PartnerContractService {

    private final PartnerContractRepository contractRepository;

    @Autowired
    public PartnerContractServiceImpl(PartnerContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public PartnerContract uploadContract(Long partnerId, MultipartFile file, String contractType, String expirationDate) {
        // Define a local directory to store files (ensure this directory exists or create it)
        String uploadDir = "uploads/contracts/";
        try {
            // Save the file locally
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);

            // Create a new PartnerContract entity
            PartnerContract contract = new PartnerContract();
            contract.setPartnerId(partnerId);
            contract.setContractType(contractType);
            contract.setFilePath(filePath);
            contract.setUploadDate(LocalDateTime.now());
            // For simplicity, parse expirationDate as LocalDateTime. In production, add proper parsing.
            contract.setExpirationDate(LocalDateTime.parse(expirationDate));
            contract.setVerificationStatus("PENDING");

            return contractRepository.save(contract);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public PartnerContract verifyContract(Long partnerId, Long contractId, String verifiedBy) {
        PartnerContract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        if (!contract.getPartnerId().equals(partnerId)) {
            throw new RuntimeException("Partner mismatch");
        }
        contract.setVerificationStatus("VERIFIED");
        contract.setVerifiedBy(verifiedBy);
        contract.setVerificationDate(LocalDateTime.now());
        return contractRepository.save(contract);
    }

    @Override
    public List<PartnerContract> getContractsByPartner(Long partnerId) {
        return contractRepository.findByPartnerId(partnerId);
    }
}
