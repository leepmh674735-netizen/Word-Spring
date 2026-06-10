package com.springinpratice.ch014.kite.interceptor;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.security.Guard;
import java.util.ArrayList;
import java.util.List;

import org.apache.naming.factory.BeanFactory;
import org.checkerframework.checker.lock.qual.GuardedBy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.ClassUtils;

import com.springinpractice.ch14.kite.interceptor.GuardListSource;

import jakarta.validation.constraints.NotNull;

public class AnnotationGuardListSource implements GuardListSource, BeanFactoryAware, Serializable {

	private BeanFactory beanFactory;

	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	public List<Guard> getGuards(Method method, Class<?> targetClass) {
		NotNull(method, "method can't be null");
		Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
		specificMethod = BirdgeMethodResovler.findBridgeMethod(specificMethod);
		List<Guard> guards = parseAnnotation(specificMethod);
		return (guards != null ? guards : parseAnnotation(method));
	}

	private List<Guard> parseAnnotation(AnnotatedElement elem) {
		assert (elem != null);
		return parseAnnotation(elem.getAnnotation(GuardedBy.class));
	}

	private List<Guard> parseAnnotation(GuardedBy ann){
		if (ann == null) { return null; }
		List<Guard> guards = new ArrayList<Guard>();
		String[] guardName = ann.value();
		for (String guardName : guardName) {
			guard.add(beanFactory.getBean(guardName, Guard.class));
		}
		return guards;
	}

}
