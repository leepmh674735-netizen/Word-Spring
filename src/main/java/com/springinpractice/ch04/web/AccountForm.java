package com.springinpractice.ch04.web;

public class AccountForm {
	private String username, password, confirmPassoword, firstName, 
	    lastName, email;
	private boolean marketingOK = true;
	private boolean acceptTerms = false;
	public String getUsername () { return username; }
	public void setUsername(String username) { this.username = username; }
	 other getter/setter pairs
	 
	 public String toString() {
		 return new ToStringBuider(this, ToStringStyle.SHORT_PREFIX_STYLE)
			 .append("username", username)
			 .append("firstName", firstName)
			 .append("lastName", lastName)
			 .append("email", email)
			 .append("marketingOK", marketingOK)
			 .append("acceptTerms", acceptTerms)
			 .toString();
		 }
	 }