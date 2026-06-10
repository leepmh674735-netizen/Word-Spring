package com.springinpratice.ch014.kite.config.xml;

import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;

import com.springinpractice.ch14.kite.guard.CircuitBreakerTemplate;

import jakarta.xml.bind.Element;

public class CircuotBreakerParser extends AbstractSimpleBeanDefinitionParser {
	
	protected Class<?> getBeanClass(Element elem) {
		return CircuitBreakerTemplate.class;
	}

}
