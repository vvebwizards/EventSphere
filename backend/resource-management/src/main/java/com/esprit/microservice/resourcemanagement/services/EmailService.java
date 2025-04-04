package com.esprit.microservice.resourcemanagement.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendBookingConfirmation(String bookedBy, String resourceName, String startTime, String endTime) {
        try {

            Context context = new Context();
            context.setVariable("resourceName", resourceName);
            context.setVariable("startTime", startTime);
            context.setVariable("endTime", endTime);
            context.setVariable("bookedBy", bookedBy);
            String body = templateEngine.process("booking-confirmation", context);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(bookedBy);
            helper.setSubject("Booking Confirmation for " + resourceName);

            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
