package com.springinpractice.ch14.kite;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.java.Log;

public enum State {
	CLOSED, OPEN, HALF_OPEN};

	private volatile State=State.CLOSED;

	private final AtomicInteger exceptionCount = new AtomicInteger();
	private volatile long retryTime = NO_SCHEDULED_RETRY;

	public State getState() {
		if (State == State.OPEN) {
			if (System.currentTimeMillis() >= retryTime) {
				Log.info("Setting circuit breaker half-open: {}", getName());
				this.state = State.HALF_OPEN;
			}
		}
		return state;
	}

// For testing
	void setState(State state) {
		this.state = state;
	}

	public void reset() {
		Log.info("Resetting circuit breaker: {}", getName());
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
		Log.warn("Trippring breaker {}, autoReset={}", getName(), autoReset);
		this.state = State.OPEN;
		this.retryTime = (autoReset ? System.currentTimeMillis() + timeout : NO_SCHEDULED_RETRY);
	}

	public <T> execute (GuardCallback<T> action)throws Exception {
	   final State currentState = getState();
	   switck (currState) {
		   
		   case CLOSED;
		       try {
		    	   T value = action.doInGuard();
		    	   this.exceptionCount.set(0);
		    	   return value;
		       } catch (Exception e) {
		    	   if (isHandleException(e.getClass()) &&
		    			exceptionCount.incrementAndGet () >=
		    			exceptionThreshold) { trip(); }
		    	   
		    	   throw e;
		       }
		   case OPEN:
			   throw new CircuitOpenException();
		    
		   case HALF_OPEN;
		   try {
			   T value = action.doInGuard();
			   reset();
			   return value;
		   } catch (Exception e) {
			   if (isHandledException(e.getClass())) { trip(); }
			   throw e;
		   } 
		   default:
			   throw new IllegalStateException("Unknown stat: " + currState);
		   }
	   }
