package com.springinpractice.ch14.kite.config.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.springinpratice.ch014.kite.config.xml.AnnotationConfigParser;
import com.springinpratice.ch014.kite.config.xml.GuardListAdviceParser;
import com.springinpratice.ch014.kite.config.xml.CircuitBreakerParser;

public class KiteNamespaceHandler extends NamespaceHandlerSupport {
    private static final Logger log = 
    		LoggerFactory.getLogger(KiteNamespaceHandler.class);
	
    @Override
    public void init() {
        log.info("Initializing KiteNamespaceHandler");
        
        registerBeanDefinitionParser("annotation-config", new AnnotationConfigParser());
        registerBeanDefinitionParser("guard-list-advice", new GuardListAdviceParser());
        registerBeanDefinitionParser("circuit-breaker", new CircuitBreakerParser());
    }
}