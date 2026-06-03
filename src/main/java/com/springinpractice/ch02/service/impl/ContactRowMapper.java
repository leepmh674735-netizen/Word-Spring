package com.springinpractice.ch02.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.spring.cho2.model.Contact;

@Component
public class ContactRowMapper implements RowMapper<Contact> {

	@Override
	public Contact mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Contact contact = new Contact();
		contact.setId(resultSet.getLong(1));
		contact.setLastName(resultSet.getString(2));
		contact.setFirstName(resultSet.getString(3));
		contact.setEmail(resultSet.getString(4));
		return contact;
	}

}