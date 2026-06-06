package com.springinpratice.ch08.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

// 누락된 Spring / Jakarta Mail 관련 임포트 추가
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

// Velocity 관련 라이브러리 임포트 (버전 및 환경에 맞춰 확인 필요)
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils; 

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

// DTO 및 예외 클래스 임포트 (패키지 경로에 맞게 자동 임포트 필요)
import com.springinpratice.ch08.domain.Subscriber;
import com.springinpratice.ch08.dao.SubscriberDao;
import com.springinpratice.ch08.exception.ConfirmationFailedException;
import com.springinpratice.ch08.exception.ConfirmationExpiredException;

@Service
@Transactional
public class MailingListServiceImpl implements MailingListService { // 1. 오타 수정: Sericelmpl -> ServiceImpl
    
    private static final String SUBSCRIBE_TEMPLATE_PATH = "mailingListSubsciotiscibe.vm";
    private static final long ONE_DAY_IN_MS = 24 * 60 * 60 * 1000;
    
    @Inject private SubscriberDao subscriberDao; // 2. 오타 수정: SubsciberDao -> SubscriberDao
    @Inject private JavaMailSender mailSender;
    @Inject private VelocityEngine velocityEngine;
    
    @Value("#{mailingListServiceProps.noReplyEmailAddress}")
    private String noReplyEmailAddress; // 3. 오타 수정: noRelyEmailAddress -> noReplyEmailAddress
    
    @Value("#{mailingListServiceProps.confirmSubsciptionUrl}")
    private String confirmSubscriptionUrl;
    
    @Value("#{mailingListServiceProps.confirmationKey}")
    private String confirmationKey;
    
    @Override
    public Subscriber getSubscriber(Long id) {
        return subscriberDao.load(id);
    }
    
    @Async
    @Transactional(dontRollbackOn = {}, rollbackOn = {Exception.class}) // 기본 트랜잭션 전파 활용 가능
    public void addSubscriber(Subscriber subscriber) { // 4. 오타 수정: addSuber -> addSubscriber
        subscriberDao.create(subscriber);
        sendConfirmSubscriptionEmail(subscriber);
    }
    
    private void sendConfirmSubscriptionEmail(Subscriber subscriber) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message); // 5. 오타 수정: MimeMessageHeloper -> MimeMessageHelper
        
        String digest = generateSubscriptionDigest(subscriber); // 6. 오타 수정: generateSubsciptionDigest
        String url = confirmSubscriptionUrl // 7. 오타 수정: confirmSudscriptionUrl
                + "?s=" + subscriber.getId() + "&d=" + digest; // URL 매개변수 연결용 &amp; -> & 수정
                
        Map<String, Object> model = new HashMap<>();
        model.put("subscriber", subscriber);
        model.put("url", url);
        
        String text = VelocityEngineUtils.mergeTemplateIntoString(
                velocityEngine, SUBSCRIBE_TEMPLATE_PATH, "UTF-8", model); // 인코딩 매개변수 추가 권장
        
        try {
            helper.setSubject("Please confirm your subscription");
            helper.setTo(subscriber.getEmail());
            helper.setFrom(noReplyEmailAddress);
            helper.setSentDate(subscriber.getDateCreated()); // 8. 오타 수정: subsciber.getDataCreated -> subscriber.getDateCreated
            helper.setText(text, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
          
        mailSender.send(message);
    }
    
    public void confirmSubscriber(Long subscriberId, String digest) // 9. 오타 수정: confirmSubScriber -> confirmSubscriber
            throws ConfirmationFailedException, ConfirmationExpiredException {
        
        Subscriber subscriber = getSubscriber(subscriberId); // 10. 오타 수정: subsciber -> subscriber
        checkTimestamp(subscriber.getDateCreated().getTime());
        
        String expectedDigest = generateSubscriptionDigest(subscriber);
        if (!digest.equals(expectedDigest)) {
            throw new ConfirmationFailedException("Bad digest");
        }
        subscriber.setConfirmed(true);
        subscriberDao.update(subscriber);
    }
    
    private String generateSubscriptionDigest(Subscriber subscriber) {
        // MD5/SHA 등 해시 생성을 위한 문자열 결합 (오타 수정: subscreber -> subscriber)
        // DigestUtils.shaHex는 예시 코드의 형태에 맞게 커스텀 처리가 필요할 수 있습니다.
        return DigestUtils.md5DigestAsHex((subscriber.getId() + ":" + confirmationKey).getBytes());
    }
    
    // 11. 메서드 문법 오류 수정: throws 선언의 올바른 위치 이동 및 중괄호 정돈
    private static void checkTimestamp(long timestamp) throws ConfirmationExpiredException {
        long now = System.currentTimeMillis();
        if (now - timestamp > ONE_DAY_IN_MS) {
            throw new ConfirmationExpiredException();
        }
    }
}