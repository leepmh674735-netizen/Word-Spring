package com.springinpractice.ch08.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springinpractice.ch02.service.impl.ContactService;

import jakarta.inject.Inject;

@Service
@Transactional(
    propagation = Propagation.REQUIRED, 
    isolation = Isolation.DEFAULT, 
    readOnly = true
)
public class ContactServiceImpl implements ContactService {

    @Inject 
    private UserMessageDao userMsgDao;

    @Override
    @Transactional(readOnly = false)
    public void saveUserMessage(UserMessage userMsg) {
        userMsgDao.create(userMsg);
    }
}