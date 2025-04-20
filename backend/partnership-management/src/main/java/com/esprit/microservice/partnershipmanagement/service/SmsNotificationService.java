package com.esprit.microservice.partnershipmanagement.service;

public interface SmsNotificationService {
    boolean sendSms(String phoneNumber, String message);
}
