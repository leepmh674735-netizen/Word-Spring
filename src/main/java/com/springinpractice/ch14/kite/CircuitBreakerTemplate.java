package com.springinpractice.ch14.kite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.springinpractice.ch14.kite.expection.CircuitOpenException;

public class CircuitBreakerTemplate extends AbstractGuard {
	private static final long NO_SCHEDULED_RETRY = Long.MAX_VALUE;
	private static final Logger log = LoggerFactory.getLogger(CircuitBreakerTemplate.class);
	
	private int exceptionThreshold = 5;
	private long timeout = 3000L;
	private List<Class<? extends Exception>> handledExceptions = 
		new ArrayList<Class<? extends Exception>>();
	
	private volatile State state = State.CLOSED;
	private final AtomicInteger exceptionCount = new AtomicInteger();
	private volatile long retryTime = NO_SCHEDULED_RETRY;
	
	public CircuitBreakerTemplate() {
		handledExceptions.add(Exception.class);
	}
	
	public int getExceptionThreshold() {
		return exceptionThreshold;
	}
	
	public void setExceptionThreshold(int threshold) {
		Assert.isTrue(threshold >= 1, "threshold must be >= 1");
		this.exceptionThreshold = threshold;
	}
	
	public long getTimeout() { 
		return timeout; 
	}
	
	public void setTimeout(long timeout) {
		Assert.isTrue(timeout >= 0L, "timeout must be >= 0");
		this.timeout = timeout;
	}
	
	public List<Class<? extends Exception>> getHandledExceptions() {
		return handledExceptions;
	}
	
	public void setHandledExceptions(List<Class<? extends Exception>> exceptions) {
		Assert.notNull(exceptions, "handledExceptions can't be null");
		this.handledExceptions = exceptions;
	}
	
	private boolean isHandleException(Class<? extends Exception> exceptionClass) {
		for (Class<? extends Exception> handledExceptionClass : handledExceptions) {
			if (handledExceptionClass.isAssignableFrom(exceptionClass)) {
				return true;
			}
		}
		return false;
	}
	
	public State getState() {
		if (state == State.OPEN) {
			if (System.currentTimeMillis() >= retryTime) {
				log.info("Setting circuit breaker half-open: {}", getName());
				this.state = State.HALF_OPEN;
			}
		}
		return state;
	}
	
	void setState(State state) {
		this.state = state;
	}
	
	public int getExceptionCount() {
		return exceptionCount.get();
	}
	
	public long getRetryTime() {
		return retryTime;
	}
	
	public void reset() {
		log.info("Resetting circuit breaker: {}", getName());
		this.state = State.CLOSED;
		this.exceptionCount.set(0);
	}
	
	public void trip() {
		trip(true);
	}
	
	public void tripWithoutAutoReset() {
		trip(false);
	}
	
	private void trip(boolean autoReset) {
		log.warn("Tripping breaker {}, autoReset={}", getName(), autoReset);
		this.state = State.OPEN;
		this.retryTime = (autoReset ? System.currentTimeMillis() + timeout : NO_SCHEDULED_RETRY);
	}
	
	@Override
	public <T> T execute(GuardCallback<T> action) throws Exception {
		final State currentState = getState();
		switch (currentState) {
			case CLOSED:
				try {
					T value = action.doInGuard();
					this.exceptionCount.set(0);
					return value;
				} catch (Exception e) {
					if (isHandleException(e.getClass()) &&
							exceptionCount.incrementAndGet() >= exceptionThreshold) {
						trip();
					}
					throw e;
				}
			case OPEN:
				throw new CircuitOpenException();
			case HALF_OPEN:
				try {
					T value = action.doInGuard();
					reset();
					return value;
				} catch (Exception e) {
					if (isHandleException(e.getClass())) {
						trip();
					}
					throw e;
				}
			default:
				throw new IllegalStateException("Unknown state: " + currentState);
		}
	}
}
