package com.esprit.microservice.partnershipmanagement.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationServiceImpl implements SmsNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(SmsNotificationServiceImpl.class);

    @Value("${twilio.phoneNumber}")
    private String fromPhoneNumber;

    @Override
    public boolean sendSms(String phoneNumber, String message) {
        try {
            Message messageResponse = Message.creator(
                            new PhoneNumber(phoneNumber),  // To number
                            new PhoneNumber(fromPhoneNumber),  // From number (your Twilio number)
                            message)
                    .create();
            logger.info("Twilio SMS sent, SID: {}", messageResponse.getSid());
            return true;
        } catch (Exception e) {
            logger.error("Failed to send SMS via Twilio", e);
            return false;
        }
    }
}
