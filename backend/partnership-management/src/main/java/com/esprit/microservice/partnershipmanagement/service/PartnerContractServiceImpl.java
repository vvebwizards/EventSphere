package com.esprit.microservice.partnershipmanagement.service;

import com.esprit.microservice.partnershipmanagement.entity.PartnerContract;
import com.esprit.microservice.partnershipmanagement.repository.PartnerContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PartnerContractServiceImpl implements PartnerContractService {

    private static final Logger logger = LoggerFactory.getLogger(PartnerContractServiceImpl.class);

    private final PartnerContractRepository contractRepository;
    private final SmsNotificationService smsNotificationService;

    // Externalized upload directory (configured in application.properties)
    @Value("${file.upload-dir}")
    private String uploadDir;

    // For demonstration, we use a default phone number for SMS.
    // You can also externalize this to application.properties.
    @Value("${default.partner.phone:+1234567890}")
    private String defaultPartnerPhone;

    @Autowired
    public PartnerContractServiceImpl(PartnerContractRepository contractRepository,
                                      SmsNotificationService smsNotificationService) {
        this.contractRepository = contractRepository;
        this.smsNotificationService = smsNotificationService;
    }

    @Override
    public PartnerContract uploadContract(Long partnerId, MultipartFile file, String contractType, String expirationDate) {
        try {
            // Ensure the upload directory exists
            File dir = new File(uploadDir);
            if (!dir.exists() && !dir.mkdirs()) {
                logger.error("Failed to create upload directory: {}", uploadDir);
                throw new RuntimeException("Failed to create upload directory");
            }

            // Build a unique file path
            String filePath = uploadDir + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File dest = new File(filePath);
            file.transferTo(dest);
            logger.info("File saved to: {}", filePath);

            // Parse the expiration date using ISO_LOCAL_DATE_TIME (expects "yyyy-MM-dd'T'HH:mm:ss")
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            // Remove any extra quotes from the expirationDate string if present.
            String cleanedExpirationDate = expirationDate.replaceAll("^\"|\"$", "");
            LocalDateTime expDate = LocalDateTime.parse(cleanedExpirationDate, formatter);

            // Create and save the contract entity
            PartnerContract contract = new PartnerContract();
            contract.setPartnerId(partnerId);
            contract.setContractType(contractType);
            contract.setFilePath(filePath);
            contract.setUploadDate(LocalDateTime.now());
            contract.setExpirationDate(expDate);
            contract.setVerificationStatus("PENDING");

            return contractRepository.save(contract);
        } catch (IOException e) {
            logger.error("Failed to store file", e);
            throw new RuntimeException("Failed to store file", e);
        } catch (Exception e) {
            logger.error("Unexpected error during contract upload", e);
            throw new RuntimeException("Unexpected error during contract upload", e);
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

        PartnerContract updatedContract = contractRepository.save(contract);
        logger.info("Contract {} verified by {}", contractId, verifiedBy);

        // Prepare SMS notification message
        String smsMessage = "Contract " + contractId + " verified";
        // For testing, we're using a default phone number.
        boolean smsSent = smsNotificationService.sendSms(defaultPartnerPhone, smsMessage);
        if (smsSent) {
            logger.info("SMS notification sent for contract verification: {}", contractId);
        } else {
            logger.warn("Failed to send SMS notification for contract verification: {}", contractId);
        }

        return updatedContract;
    }

    @Override
    public List<PartnerContract> getContractsByPartner(Long partnerId) {
        return contractRepository.findByPartnerId(partnerId);
    }
}
