package com.springinpractice.dao.hbn;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.spring.cho2.model.Contact;
import com.springinpractice.dao.ContactDao;
import com.springinpractice.dao.hibernate.AbstractHbnDao;

@Repository
public class HbnContactDao extends AbstractHbnDao<Contact> 
        implements ContactDao {
	
	@SuppressWarnings("unchecked")
	public List<Contact> findByEmail(String email){
		return getSession()
				.getNamedQuery("findContactByEmail")
				.setString("email","%"+ email + "%")
				.list();
	}
}
