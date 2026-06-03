package com.springinpractice.ch02.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.cho2.model.Contact;
import com.springinpractice.dao.ContactDao;

import jakarta.inject.Inject;

@Service
@Transactional
public class ContactServiceImpl implements ContactService {

	@Inject 
	private ContactDao contactDao;
	
	public void createContact(Contact contact) {
		contactDao.create(contact);
	}

	public List<Contact> getContacts() {
		return contactDao.getAll();
	}

	public List<Contact> getContactsByEmail(String email){
		return contactDao.findByEmail(email);
	}

	public Contact getContact(Long id) {
		return contactDao.get(id);
	}

	public void updateContact(Contact contact) {
		contactDao.update(contact);
	}
	
	public void deleteContact(Long id) {
		contactDao.deleteById(id);
	}
}