package com.springinpratice.ch07.service;

import com.springinpratice.ch07.domain.Message;

public interface ForumService {
	void createMessage(Message message);
	void setMessageVisible(Message message);
	void deleteMessage(Message message);
}
