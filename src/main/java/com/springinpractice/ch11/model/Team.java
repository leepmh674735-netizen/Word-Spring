package com.springinpractice.ch11.model;

import org.springframework.stereotype.Indexed;

import jakarta.validation.constraints.Size;

public class Team extends AbstractCI<Team>{
	
	@Indexed(indexType = IndexType.FULLTEXT, indexName="findByName")
	private String name;
	
	@Size(max = 80)
	public String getName () { return name; }
	
	public void setName(String name) { this.name = name; }
	// compareTO and equals
	
	public String getDispalyName() { return name; }
	

}
