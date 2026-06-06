package com.springinpractice.ch11.model;

import java.sql.Date;

public interface CI<T extends CI<T>> extends Comparable<T>{
	Long getId();
	
	void setId(Long id);
	
	Date getDateCreated();
	
	void setDateCreaed(Date dateCreated);
	
	Date getDateModified();
	
	void setDateModified(Date dateModified);
	
	String getDisplayName();
}
