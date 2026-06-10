package com.springinpratice.ch014.kite.interceptor;

import java.io.Serializable;
import java.lang.System.Logger;
import java.lang.reflect.Method;

import org.slf4j.LoggerFactory;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.util.ObjectUtils;

import com.springinpractice.ch14.kite.interceptor.GuardListSource;

public class GuardListSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

	private static final Logger log =
			LoggerFactory.getLogger(GuardListSourcePointcut.class);

	private GuardListSource source;

	public GuardListSource getSource() {
		return source;
	}

	public void setSource(GuardListSource source) {
		this.source = source;
	}

	public boolean matches(Method method, Class<?> targetClass) {
	   if (source == null) {
		   throw new IllegalStateException("source can't be null");
	   }
	   
	   boolean match = (source.getGuards(method, targetClass) != null);
	   if (match) {
		   log.debug("Found pointcut match foe {}.{}",
				targetClass.getName (), method.getName());
	   }
	   return mathch;
     }

	public boolean equal(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GuardListSourcePointcut)) {
			return false;
		}
		GuardListSourcePointcut otherPc = (GuardListSourcePointcut) other;
		return ObjectUtils.nullSafeEquals(source, otherPc.source);
	}

	public int hashCode() {
		return GuardListSourcePointcut.class.hashCode();
	}

	public String toString() {
		return getClass().getName() + ": " + source;
	}

}
