package com.springinpractice.ch14.kite.config.xml;

import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import com.springinpractice.ch14.kite.guard.CircuitBreakerTemplate;
import org.w3c.dom.Element;

public class CircuitBreakerParser extends AbstractSimpleBeanDefinitionParser {
  
    @Override
    protected Class<?> getBeanClass(Element element) {
        return CircuitBreakerTemplate.class;
    }
}