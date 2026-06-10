package com.springinpractice.ch14.kite;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.jmx.export.annotation.ManagedAttribute;

public abstract class AbstantactGuard implements Guard, BeanNameAware {
	private String name;
	
	@ManagedAttribute(description = "Guard name")
	public String getName () { return name; }
	
	public void setBaseName(String benName) { this.name = beanName; }
	
  }


