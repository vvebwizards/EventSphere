package com.esprit.microservice.partnershipmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partner_communications")
public class PartnerCommunication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long partnerId;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    private LocalDateTime communicationDate;

    private String status; // e.g., SENT, PENDING

    // Constructors
    public PartnerCommunication() {
    }

    public PartnerCommunication(Long partnerId, String subject, String message, LocalDateTime communicationDate, String status) {
        this.partnerId = partnerId;
        this.subject = subject;
        this.message = message;
        this.communicationDate = communicationDate;
        this.status = status;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCommunicationDate() {
        return communicationDate;
    }

    public void setCommunicationDate(LocalDateTime communicationDate) {
        this.communicationDate = communicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
