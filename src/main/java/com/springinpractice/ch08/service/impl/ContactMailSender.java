package com.springinpractice.ch08.service.impl;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.springinpractice.ch08.domain.UserMessage;

@Component
public class ContactMailSender {

    private static final Logger log = LoggerFactory.getLogger(ContactMailSender.class);

    private boolean sendConfirmation = true;
    private boolean notifyAdmin = true;
    private String noReplyEmailAddr = "noreply@example.com";
    private String adminEmailAddr = "admin@example.com";

    public void sendEmail(UserMessage userMsg) {
        log.info("Sending email for user message: {}", userMsg);
        
        if (sendConfirmation) {
            log.info("Confirmation sent to: {} from: {}", userMsg.getEmail(), noReplyEmailAddr);
        }
        
        if (notifyAdmin) {
            log.info("Admin notification sent to: {} from: {}", adminEmailAddr, userMsg.getEmail());
        }
    }
}