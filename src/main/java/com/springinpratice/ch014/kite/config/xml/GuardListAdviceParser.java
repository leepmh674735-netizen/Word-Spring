package com.springinpratice.ch014.kite.config.xml;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;

import com.springinpractice.ch14.kite.interceptor.GuardListInterceptor;
import com.springinpractice.ch14.kite.interceptor.DefaultGuardListSource;

import org.w3c.dom.Element;

public class GuardListAdviceParser extends AbstractSingleBeanDefinitionParser {
	
	@Override
	protected Class<?> getBeanClass(Element elem) {
		return GuardListInterceptor.class;
	}
	
	@Override
	protected void doParse(Element elem, BeanDefinitionBuilder builder) {
		builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		
		RootBeanDefinition srcDef = 
				new RootBeanDefinition(DefaultGuardListSource.class);
		srcDef.setSource(elem);
		srcDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		srcDef.getPropertyValues().add("guards",
				new RuntimeBeanReference(elem.getAttribute("guards")));
		builder.addPropertyValue("source", srcDef);
	}
}
