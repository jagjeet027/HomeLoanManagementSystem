package com.homeLoan.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String to, String message) {
        log.info("Sending email to: {}", to);
        log.info("Message: {}", message);
        log.info("Email sent successfully!");
    }
}