package com.esprit.microservice.partnershipmanagement.service;

import org.springframework.stereotype.Service;

@Service
public class SmsNotificationServiceImpl implements SmsNotificationService {

    @Override
    public boolean sendSms(String phoneNumber, String message) {
        // In a real implementation, integrate with an SMS API (e.g., Twilio).
        // For now, simply log the message and return true.
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
        return true;
    }
}
