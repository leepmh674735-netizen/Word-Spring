package com.springinpractice.ch04.web;

public class AccountForm {
	private String username;
	private String password;
	private String confirmPassoword;
	private String firstName;
	private String lastName;
	private String email;
	private boolean marketingOK = true;
	private boolean acceptTerms = false;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassoword() {
		return confirmPassoword;
	}

	public void setConfirmPassoword(String confirmPassoword) {
		this.confirmPassoword = confirmPassoword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isMarketingOK() {
		return marketingOK;
	}

	public void setMarketingOK(boolean marketingOK) {
		this.marketingOK = marketingOK;
	}

	public boolean isAcceptTerms() {
		return acceptTerms;
	}

	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	@Override
	public String toString() {
		return "AccountForm [username=" + username + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", marketingOK=" + marketingOK + ", acceptTerms=" + acceptTerms + "]";
	}
}