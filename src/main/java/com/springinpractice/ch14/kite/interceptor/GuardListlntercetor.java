package com.springinpractice.ch14.kite.interceptor;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.springinpractice.ch14.kite.Guard;
import com.springinpractice.ch14.kite.GuardCallback;

public class GuardListInterceptor implements MethodInterceptor {
	private static final Logger log = LoggerFactory.getLogger(GuardListInterceptor.class);

	private GuardListSource source;

	public GuardListSource getSource() {
		return source;
	}

	public void setSource(GuardListSource source) {
		this.source = source;
	}

	@Override
	public Object invoke(final MethodInvocation invocation) throws Throwable {
		List<Guard> guards = getGuards(invocation);

		if (guards == null || guards.isEmpty()) {
			if (log.isDebugEnabled()) {
				log.debug("Executing method {} without guards", invocation.getMethod().getName());
			}
			return invocation.proceed();
		}

		LinkedList<Guard> guardStack = new LinkedList<Guard>(guards);
		Collections.reverse(guardStack);

		Guard lastGuard = guardStack.pop();
		Interceptor interceptor = new LastInterceptor(lastGuard, invocation);

		while (!guardStack.isEmpty()) {
			Guard guard = guardStack.pop();
			interceptor = new NotLastInterceptor(guard, interceptor);
		}
		return interceptor.invoke();
	}

	private List<Guard> getGuards(MethodInvocation invocation) {
		Method method = invocation.getMethod();
		Object thisObj = invocation.getThis();
		Class<?> clazz = (thisObj != null ? thisObj.getClass() : null);
		return source.getGuards(method, clazz);
	}

	private interface Interceptor {
		Object invoke() throws Throwable;
	}

	private static class NotLastInterceptor implements Interceptor {
		private Guard guard;
		private Interceptor interceptor;

		public NotLastInterceptor(Guard guard, Interceptor interceptor) {
			this.guard = guard;
			this.interceptor = interceptor;
		}

		public Object invoke() throws Throwable {
			return guard.execute(new GuardCallback<Object>() {
				
				@Override
				public Object doInGuard() throws Exception {
					try {
						log.debug("Entered guard: {}", guard.getName());
						return interceptor.invoke();
					} catch (Exception e) {
						throw e;
					} catch (Error e) {
						throw e;
					} catch (Throwable t) {
						throw new RuntimeException(t);
					} finally {
						log.debug("Exiting guard: {}", guard.getName());
					}
				}
			});
		}
	}

	private static class LastInterceptor implements Interceptor {
		private Guard guard;
		private MethodInvocation invocation;

		public LastInterceptor(Guard guard, MethodInvocation invocation) {
			this.guard = guard;
			this.invocation = invocation;
		}

		public Object invoke() throws Throwable {
			return guard.execute(new GuardCallback<Object>() {
				
				@Override
				public Object doInGuard() throws Exception {
					try {
						log.debug("Entered guard: {}", guard.getName());
						log.debug("Executing target method: {}", invocation.getMethod().getName());
						return invocation.proceed();
					} catch (Exception e) {
						throw e;
					} catch (Error e) {
						throw e;
					} catch (Throwable t) {
						throw new RuntimeException(t);
					} finally {
						log.debug("Exiting guard: {}", guard.getName());
					}
				}
			});
		}
	}
}