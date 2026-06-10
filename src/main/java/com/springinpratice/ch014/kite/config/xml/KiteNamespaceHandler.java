package com.springinpratice.ch014.kite.config.xml;

import java.lang.System.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class KiteNamespaceHandler extends NamespaceHandlerSupport {
	private static Logger log =
			LoggerFactory.getILogger(KiteNamespaceHadler");
		
	        public void init () {
				log.info("Initializing KiteNamwspaceHandler");
				registerBeanDefintionParser(
						"annotation-config", new AnnnotationConfigParser());
				registerBeanDefinitionParser(
						"guard-list-advice", new GuardListAdviceParser());
				registerBeanDefinitionParser(
						"circuit-breaker", new CircuitBreakerParser ());	
		        }

             }