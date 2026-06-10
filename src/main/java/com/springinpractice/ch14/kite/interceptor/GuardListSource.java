package com.springinpractice.ch14.kite.interceptor;

import java.lang.reflect.Method;
import java.security.Guard;
import java.util.List;

public interface GuardListSource {
	
	List<Guard> getGuards(Method method, Class<?> targetClass);

}
