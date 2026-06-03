package com.springinpractice.ch02.dao.jpa;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.cho2.model.Contact;
import com.springinpractice.dao.ContactDao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class JpaContactDao implements ContactDao {

	@PersistenceContext 
	private EntityManager entityManager;
	
	public void create(Contact contact) {
		entityManager.persist(contact);
	}

	public Contact get(Serializable id) {
		return entityManager.find(Contact.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Contact> getAll() {
		return (List<Contact>) entityManager
				.createQuery("from Contact")
				.getResultList();
	}
}