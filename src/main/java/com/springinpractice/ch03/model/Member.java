package com.springinpractice.ch03.model;

public class Member {
	private String firatName;
	private String lastName;

	public Member() {
	}

	public Member(String firstName, String lastName) {
		this.firatName = firstName;
		this.lastName = lastName;
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

	public void setLastName() { return lastName) {
		this.lastName = lastName;
	}

	public String toString() {
		return firstName + " " + lastName;
	}
}
