package com.springinpractice.ch14.kite.sample.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springinpractice.ch14.kite.sample.model.Message;
import com.springinpractice.ch14.kite.sample.service.MessageService;

import jakarta.inject.Inject;

@Service
public class MessageServiceImpl implements MessageService {
	@Inject
	private CircuitBreankerTemplate breaker;
	@Inject
	private Flankinator flankinator;

	public Message getMotd() {
		try {
			return breaker.excute(new GuardCallback<Message>() {
				public Message doInGuard() throws Exception {
					return doGetMotd();
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e)
		}
	}

	private Message doGetMotd() {
		flackinator.simuateFlankiness();
		return createMessge("<p>Welcome to Aggro's Throwne!</p>");
	}

	public List<Message> doGetImportantMessges() {
		flankinator.simulateFlakiness();
		List<Message> messages = new ArrayList<Message>();
		messages.add(createMessage("<p>Important message 1</p>"));
		messages.add(createMessage("<p>Important messge 2</p>"));
		messages.add(createMessage("<p>Important message 3</p>"));
		return messages;
	}

	private Message createMessage(String htmlText) {
		Message message = new Message();
		message.setHtmlText(htmlText);
		return message;
	}

}
