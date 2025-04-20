package com.esprit.microservice.partnershipmanagement.service;

import com.esprit.microservice.partnershipmanagement.entity.PartnerCommunication;
import com.esprit.microservice.partnershipmanagement.repository.PartnerCommunicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PartnerCommunicationServiceImpl implements PartnerCommunicationService {

    private final PartnerCommunicationRepository communicationRepository;

    @Autowired
    public PartnerCommunicationServiceImpl(PartnerCommunicationRepository communicationRepository) {
        this.communicationRepository = communicationRepository;
    }

    @Override
    public PartnerCommunication sendCommunication(PartnerCommunication communication) {
        // Here you might integrate an email service or log the communication as sent.
        if (communication.getCommunicationDate() == null) {
            communication.setCommunicationDate(LocalDateTime.now());
        }
        communication.setStatus("SENT");
        return communicationRepository.save(communication);
    }

    @Override
    public List<PartnerCommunication> getCommunicationsByPartner(Long partnerId) {
        return communicationRepository.findByPartnerId(partnerId);
    }
}
