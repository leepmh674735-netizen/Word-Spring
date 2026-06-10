package com.springinpractice.ch14.kite.sample.service;

import java.util.List;

import com.springinpractice.ch14.kite.sample.model.Message;

public interface MessageService {
	
	Message getMothid ();
	
	List<Message> getImportantMessages();
}
