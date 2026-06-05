package com.springinpractice.ch08.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import jakarta.inject.Inject;
import jakarta.mail.internet.MimeMessage;

@Component
public class ContactMailSender {

    private static final String CONFIRMATION_TEMPLATE_PATH = "contactConfirm.vm";
    private static final String USER_MSG_TEMPLATE_PATH = "contactUserMessage.vm";

    @Inject 
    private JavaMailSender mailSender;
    
    @Inject 
    private VelocityEngine velocityEngine;
    
    @Value("#{contactServiceProps.sendConfirmation}")
    private boolean sendConfirmation;
    
    @Value("#{contactServiceProps.notifyAdmin}")
    private boolean notifyAdmin;
    
    @Value("#{contactServiceProps.noReplyEmailAddress}")
    private String noReplyEmailAddr;
    
    @Value("#{contactServiceProps.adminEmailAddress}")
    private String adminEmailAddr;
    
    @Async
    public void sendEmail(UserMessage userMsg) {
        if (sendConfirmation) {
            MimeMessage confirmationMsg = createEmail(
                userMsg, 
                CONFIRMATION_TEMPLATE_PATH, 
                "Confirmation message", 
                userMsg.getEmail(), 
                noReplyEmailAddr, 
                null
            );
            sendMail(confirmationMsg);
        }
        
        if (notifyAdmin) {
            MimeMessage adminMsg = createEmail(
                userMsg, 
                USER_MSG_TEMPLATE_PATH, 
                "New user message", 
                adminEmailAddr, 
                userMsg.getEmail(), 
                userMsg.getName()
            );
            sendMail(adminMsg);
        }
    }

    private MimeMessage createEmail(UserMessage userMsg, String templatePath, String subject, String toEmail, String fromEmail, String fromName) {
        MimeMessage mimeMsg = mailSender.createMimeMessage();
        
        Map<String, Object> model = new HashMap<>();
        model.put("userMessage", userMsg);
        
        @SuppressWarnings("deprecation")
        String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templatePath, "UTF-8", model);
        text = text.replaceAll("\n", "<br>");
        
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMsg, true, "UTF-8");
            helper.setSubject(subject);
            helper.setTo(toEmail);
            helper.setText(text, true);
            
            if (fromName == null) {
                helper.setFrom(fromEmail);
            } else {
                helper.setFrom(fromEmail, fromName);
            }
            
            if (userMsg.getDateCreated() != null) {
                helper.setSentDate(userMsg.getDateCreated());
            }
            
            return mimeMsg;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMail(MimeMessage mimeMsg) {
        mailSender.send(mimeMsg);
    }
}