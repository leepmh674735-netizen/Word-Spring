package com.springinpractice.ch08.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.springinpractice.ch02.service.impl.ContactService;

import jakarta.inject.Inject;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
@Transactional(
    propagation = Propagation.REQUIRED,
    isolation = Isolation.DEFAULT,
    readOnly = true
)
public class ContactServiceImpl implements ContactService {

    private static final String CONFIRMATION_TEMPLATE_PATH = "contactConfirm.vm";

    @Inject
    private UserMessageDao userMsgDao;
    
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

    @Override
    @Transactional(readOnly = false)
    public void saveUserMessage(UserMessage userMsg) {
        if (userMsg == null) {
            throw new IllegalArgumentException("userMsg can't be null");
        }
        
        userMsgDao.create(userMsg);
        
        if (sendConfirmation) {
            MimeMessage mimeMsg = createEmail(
                userMsg, 
                CONFIRMATION_TEMPLATE_PATH, 
                "Confirmation message", 
                userMsg.getEmail(), 
                noReplyEmailAddr, 
                null
            );
            sendEmail(mimeMsg);
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

    private void sendEmail(MimeMessage mimeMsg) {
        mailSender.send(mimeMsg);
    }
}