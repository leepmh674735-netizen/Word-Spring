package com.springinpractice.ch14.kite.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springinpractice.ch14.kite.GuardCallback;
import com.springinpractice.ch14.kite.CircuitBreakerTemplate;
import com.springinpractice.ch14.kite.sample.model.Message;
import com.springinpractice.ch14.kite.sample.service.MessageService;

import jakarta.inject.Inject;

@Service
public class MessageServiceImpl implements MessageService {
	@Inject
	private CircuitBreakerTemplate breaker;
	@Inject
	private Flankinator flankinator;

	@Override
	public Message getMotd() {
		try {
			return breaker.execute(new GuardCallback<Message>() {
				@Override
				public Message doInGuard() throws Exception {
					return doGetMotd();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Message doGetMotd() {
		flankinator.simulateFlakiness();
		return createMessage("<p>Welcome to Aggro's Throne!</p>");
	}

	@Override
	public List<Message> getImportantMessages() {
		return doGetImportantMessages();
	}

	private List<Message> doGetImportantMessages() {
		flankinator.simulateFlakiness();
		List<Message> messages = new ArrayList<Message>();
		messages.add(createMessage("<p>Important message 1</p>"));
		messages.add(createMessage("<p>Important message 2</p>"));
		messages.add(createMessage("<p>Important message 3</p>"));
		return messages;
	}

	private Message createMessage(String htmlText) {
		Message message = new Message();
		message.setHtmlText(htmlText);
		return message;
	}
}
