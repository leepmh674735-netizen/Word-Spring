package com.springinpractice.ch11.model;

import org.springframework.stereotype.Indexed;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Application extends AbstractCI<Application>{
	@Indexed private String name;
	private String shortDescription;
	
	@NotNull
	@Size(max = 80)
	public String getName() { return name; }
	
	public void setName(String name) { this.name = name; }
	
	@Size(max = 200)
	public String getShortDescription () { return shortDescription; }
	
	public void setShortDescirtion(String shortDescription) {
		this.shortDescription = shortDescription;	
	}
	public String getDisplayName() {
		return "Application";
	}

}
