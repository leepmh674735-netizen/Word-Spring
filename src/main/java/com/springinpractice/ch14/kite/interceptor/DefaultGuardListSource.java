package com.springinpractice.ch14.kite.interceptor;

import java.lang.reflect.Method;
import com.springinpractice.ch14.kite.Guard;
import java.util.List;

public class DefaultGuardListSource implements GuardListSource {
	private List<Guard> guards;
	
	public List<Guard> getGuards() { return guards; }
	
	public void setGuards (List<Guard> guards) { this.guards = guards; }
	
	public List<Guard> getGuards (Method method, Class<?> targetClass) {
		return guards;
	}
}
