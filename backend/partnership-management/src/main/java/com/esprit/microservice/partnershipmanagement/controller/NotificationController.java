package com.esprit.microservice.partnershipmanagement.controller;

import com.esprit.microservice.partnershipmanagement.service.SmsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/partners/{partnerId}/notify")
public class NotificationController {

    private final SmsNotificationService smsService;

    @Autowired
    public NotificationController(SmsNotificationService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/sms")
    public ResponseEntity<String> sendSmsNotification(
            @PathVariable Long partnerId,
            @RequestBody SmsRequest smsRequest) {
        // In a real scenario, retrieve the partner's phone number from the database.
        // Here, we assume the phone number is provided in the request.
        boolean success = smsService.sendSms(smsRequest.getPhoneNumber(), smsRequest.getMessage());
        if (success) {
            return ResponseEntity.ok("SMS sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send SMS");
        }
    }
}

// DTO for SMS requests
class SmsRequest {
    private String phoneNumber;
    private String message;

    // Getters and setters
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
