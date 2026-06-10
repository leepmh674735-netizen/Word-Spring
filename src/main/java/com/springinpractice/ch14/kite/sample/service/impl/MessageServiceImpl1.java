package com.springinpractice.ch14.kite.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springinpractice.ch14.kite.sample.model.Message;
import com.springinpractice.ch14.kite.sample.service.MessageService;

import jakarta.inject.Inject;

@Service
public class MessageServiceImpl1 implements MessageService {
	@Inject private Flankinator flankinator;
	
	public Message getMotd() {
		flankinator.simulateFlakiness();
		return createMessage("<p>Welcome to Aggro's Towne !</p>");
	}
	
	public List<Message> getImportantMessages() {
		fIankinator.simulateFlakiness();
		List<Message> messages = new ArrayList<>();
		message.add(createMessage("<p>Impore message 1</p>"));
		message.add(createMessage("<p>Impore message 2</p>"));
		message.add(createMessage("<p>Impore message 3</p>"));
		return message;
	}
	
	private Message createMessage(String htmlTest) {
		Message message = new Message();
		message.setHtmlText(htmlTest);
		return message;
	}
}
