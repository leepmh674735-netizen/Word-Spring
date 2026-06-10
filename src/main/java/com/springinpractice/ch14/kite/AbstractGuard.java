package com.springinpractice.ch14.kite;

import org.springframework.beans.factory.BeanNameAware;

public abstract class AbstractGuard implements Guard, BeanNameAware {
	private String name;

	public String getName() {
		return name;
	}

	public void setBaseName(String beanName) {
		this.name = beanName;
	}
}
