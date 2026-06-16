package com.springinpratice.ch07.dao;

import com.springinpratice.ch07.domain.Message;

public interface MessageDao {
	void create(Message message);
	Message get(Long id);
	void update(Message message);
	void delete(Message message);
}
