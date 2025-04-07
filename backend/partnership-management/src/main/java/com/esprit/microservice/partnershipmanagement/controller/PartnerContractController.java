package com.esprit.microservice.partnershipmanagement.controller;

import com.esprit.microservice.partnershipmanagement.entity.PartnerContract;
import com.esprit.microservice.partnershipmanagement.service.PartnerContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/partners/{partnerId}/contracts")
public class PartnerContractController {

    private final PartnerContractService contractService;

    @Autowired
    public PartnerContractController(PartnerContractService contractService) {
        this.contractService = contractService;
    }

    // Upload contract endpoint (multipart/form-data)
    @PostMapping
    public ResponseEntity<PartnerContract> uploadContract(
            @PathVariable Long partnerId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("contractType") String contractType,
            @RequestParam("expirationDate") String expirationDate) {
        PartnerContract contract = contractService.uploadContract(partnerId, file, contractType, expirationDate);
        return new ResponseEntity<>(contract, HttpStatus.CREATED);
    }

    // Verify contract endpoint
    @PutMapping("/{contractId}/verify")
    public ResponseEntity<PartnerContract> verifyContract(
            @PathVariable Long partnerId,
            @PathVariable Long contractId,
            @RequestBody VerifyRequest verifyRequest) {
        PartnerContract contract = contractService.verifyContract(partnerId, contractId, verifyRequest.getVerifiedBy());
        return new ResponseEntity<>(contract, HttpStatus.OK);
    }

    // Retrieve contracts for a partner
    @GetMapping
    public ResponseEntity<List<PartnerContract>> getContracts(@PathVariable Long partnerId) {
        List<PartnerContract> contracts = contractService.getContractsByPartner(partnerId);
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }
}

// DTO for verification request
class VerifyRequest {
    private String verifiedBy;

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
}
