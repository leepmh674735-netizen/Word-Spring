package com.springinpractice.dao;

import java.util.List;

import com.spring.cho2.model.Contact;

public interface ContactDao extends Dao<Contact> {
	List<Contact> findByEmail(String email);

}
