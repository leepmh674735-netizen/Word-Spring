package com.springinpractice.ch02.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.cho2.model.Contact;

public interface ContactDao extends JpaRepository<Contact,Long>{
	List<Contact> findByEmailLike(String email);

}
