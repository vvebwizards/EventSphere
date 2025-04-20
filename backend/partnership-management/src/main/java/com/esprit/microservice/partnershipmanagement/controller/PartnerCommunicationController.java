package com.esprit.microservice.partnershipmanagement.controller;

import com.esprit.microservice.partnershipmanagement.entity.PartnerCommunication;
import com.esprit.microservice.partnershipmanagement.service.PartnerCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/partners/{partnerId}/communications")
public class PartnerCommunicationController {

    private final PartnerCommunicationService communicationService;

    @Autowired
    public PartnerCommunicationController(PartnerCommunicationService communicationService) {
        this.communicationService = communicationService;
    }

    @PostMapping
    public ResponseEntity<PartnerCommunication> sendCommunication(
            @PathVariable Long partnerId,
            @RequestBody PartnerCommunication communication) {
        communication.setPartnerId(partnerId);
        PartnerCommunication sent = communicationService.sendCommunication(communication);
        return new ResponseEntity<>(sent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PartnerCommunication>> getCommunications(@PathVariable Long partnerId) {
        List<PartnerCommunication> communications = communicationService.getCommunicationsByPartner(partnerId);
        return new ResponseEntity<>(communications, HttpStatus.OK);
    }
}
