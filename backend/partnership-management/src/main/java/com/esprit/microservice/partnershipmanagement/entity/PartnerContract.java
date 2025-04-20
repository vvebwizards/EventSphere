package com.esprit.microservice.partnershipmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partner_contracts")
public class PartnerContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long partnerId;

    private String contractType;

    // Assume the file is stored locally and filePath stores the path or URL.
    private String filePath;

    private LocalDateTime uploadDate;

    private LocalDateTime expirationDate;

    private String verificationStatus; // e.g., PENDING, VERIFIED

    private String verifiedBy;

    private LocalDateTime verificationDate;

    // Constructors
    public PartnerContract() {
    }

    public PartnerContract(Long partnerId, String contractType, String filePath, LocalDateTime uploadDate,
                           LocalDateTime expirationDate, String verificationStatus) {
        this.partnerId = partnerId;
        this.contractType = contractType;
        this.filePath = filePath;
        this.uploadDate = uploadDate;
        this.expirationDate = expirationDate;
        this.verificationStatus = verificationStatus;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public LocalDateTime getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(LocalDateTime verificationDate) {
        this.verificationDate = verificationDate;
    }
}
