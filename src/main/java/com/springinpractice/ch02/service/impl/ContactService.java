package com.springinpractice.ch02.service.impl;

import java.util.List;

import com.spring.cho2.model.Contact;

public interface ContactService {
	
	void createContact(Contact contact);
	
	List<Contact> getContacts();
	
	List<Contact> getContactsByEmail(String email);
	
	Contact getContact(Contact contact);
	
	void deleteContact(Long id);

}
