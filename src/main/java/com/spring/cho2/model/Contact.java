package com.spring.cho2.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.util.StringUtils;

public class Contact {
	private Long id;
	private String lastName;
	private String firstName;
	private String middleInitial;
	private String email;
	
	public Long getId() { 
		return id; 
	}

	public void setId(Long id) { 
		this.id = id; 
	}
	
	@NotNull
	@Size(min = 1, max = 40)
	public String getLastName() { 
		return lastName; 
	}
	
	public void setLastName(String lastName) {
		this.lastName = StringUtils.hasText(lastName) ? lastName.trim() : lastName;
	}
	
	@NotNull
	@Size(min = 1, max = 40)
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = StringUtils.hasText(firstName) ? firstName.trim() : firstName;
	}
	
	@Size(max = 1)
	public String getMiddleInitial() {
		return middleInitial; 
	}
	
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = StringUtils.hasText(middleInitial) ? middleInitial.trim() : middleInitial;
	}
	
	@Email
	public String getEmail() { 
		return email; 
	}
	
	public void setEmail(String email) {
		this.email = StringUtils.hasText(email) ? email.trim() : email;
	}
	
	public String getFullName() {
		String fullName = lastName + ", " + firstName;
		if (StringUtils.hasText(middleInitial)) {
			fullName += " " + middleInitial + ".";
		}
		return fullName;
	}
	
	@Override
	public String toString() {
		return "[Contact: id=" + id
			+ ", firstName=" + firstName
			+ ", middleInitial=" + middleInitial
			+ ", lastName=" + lastName
			+ ", email=" + email
			+ "]";
	}
}