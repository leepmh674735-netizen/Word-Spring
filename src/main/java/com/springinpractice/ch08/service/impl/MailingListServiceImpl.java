package com.springinpractice.ch08.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import com.springinpractice.ch08.domain.Subscriber;
import com.springinpractice.ch08.dao.SubscriberDao;
import com.springinpractice.ch08.exception.ConfirmationFailedException;
import com.springinpractice.ch08.exception.ConfirmationExpiredException;
import com.springinpractice.ch08.service.MailingListService;

@Service
@Transactional
public class MailingListServiceImpl implements MailingListService {
    
    private static final String SUBSCRIBE_TEMPLATE_PATH = "mailingListSubsciotiscibe.vm";
    private static final long ONE_DAY_IN_MS = 24 * 60 * 60 * 1000;
    
    @Inject private SubscriberDao subscriberDao;
    @Inject private JavaMailSender mailSender;
    @Inject private VelocityEngine velocityEngine;
    
    @Value("${app.mailinglist.noReplyEmailAddress:noreply@localhost}")
    private String noReplyEmailAddress;
    
    @Value("${app.mailinglist.confirmSubscriptionUrl:http://localhost:8181/mailinglist/subscribe-confirm}")
    private String confirmSubscriptionUrl;
    
    @Value("${app.mailinglist.confirmationKey:supersecretkey}")
    private String confirmationKey;
    
    @Override
    public Subscriber getSubscriber(Long id) {
        return subscriberDao.load(id);
    }
    
    @Async
    @Transactional(dontRollbackOn = {}, rollbackOn = {Exception.class})
    public void addSubscriber(Subscriber subscriber) {
        subscriberDao.create(subscriber);
        sendConfirmSubscriptionEmail(subscriber);
    }
    
    private void sendConfirmSubscriptionEmail(Subscriber subscriber) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        String digest = generateSubscriptionDigest(subscriber);
        String url = confirmSubscriptionUrl + "?s=" + subscriber.getId() + "&d=" + digest;
                
        org.apache.velocity.VelocityContext context = new org.apache.velocity.VelocityContext();
        context.put("subscriber", subscriber);
        context.put("url", url);
        
        try {
            org.apache.velocity.Template template = velocityEngine.getTemplate(SUBSCRIBE_TEMPLATE_PATH, "UTF-8");
            java.io.StringWriter writer = new java.io.StringWriter();
            template.merge(context, writer);
            String text = writer.toString();
            
            helper.setSubject("Please confirm your subscription");
            helper.setTo(subscriber.getEmail());
            helper.setFrom(noReplyEmailAddress);
            helper.setSentDate(subscriber.getDateCreated());
            helper.setText(text, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
          
        mailSender.send(message);
    }
    
    public void confirmSubscriber(Long subscriberId, String digest)
            throws ConfirmationFailedException, ConfirmationExpiredException {
        
        Subscriber subscriber = getSubscriber(subscriberId);
        checkTimestamp(subscriber.getDateCreated().getTime());
        
        String expectedDigest = generateSubscriptionDigest(subscriber);
        if (!digest.equals(expectedDigest)) {
            throw new ConfirmationFailedException("Bad digest");
        }
        subscriber.setConfirmed(true);
        subscriberDao.update(subscriber);
    }
    
    private String generateSubscriptionDigest(Subscriber subscriber) {
        return DigestUtils.md5DigestAsHex((subscriber.getId() + ":" + confirmationKey).getBytes());
    }
    
    private static void checkTimestamp(long timestamp) throws ConfirmationExpiredException {
        long now = System.currentTimeMillis();
        if (now - timestamp > ONE_DAY_IN_MS) {
            throw new ConfirmationExpiredException();
        }
    }
}
