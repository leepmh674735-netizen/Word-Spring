package com.springinpractice.ch08.service;

import com.springinpractice.ch08.domain.Subscriber;
import com.springinpractice.ch08.exception.ConfirmationExpiredException;
import com.springinpractice.ch08.exception.ConfirmationFailedException;

public interface MailingListService {
    Subscriber getSubscriber(Long id);
    void addSubscriber(Subscriber subscriber);
    void confirmSubscriber(Long subscriberId, String digest)
            throws ConfirmationFailedException, ConfirmationExpiredException;
}
