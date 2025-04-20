package com.esprit.microservice.partnershipmanagement.service;

import com.esprit.microservice.partnershipmanagement.entity.PartnerCommunication;

import java.util.List;

public interface PartnerCommunicationService {
    PartnerCommunication sendCommunication(PartnerCommunication communication);
    List<PartnerCommunication> getCommunicationsByPartner(Long partnerId);
}
