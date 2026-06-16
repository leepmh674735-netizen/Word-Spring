package com.springinpractice.ch08.service;

import com.springinpractice.ch08.domain.UserMessage;

public interface ContactService {
    void saveUserMessage(UserMessage userMsg);
    void saveMessage(UserMessage userMsg);
}
