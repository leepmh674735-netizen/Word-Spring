package com.springinpractice.ch14.kite.guard;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(description = "Circuit Breaker Guard")
public class CircuitBreakerTemplate extends com.springinpractice.ch14.kite.CircuitBreakerTemplate {
	
	@Override
	@ManagedAttribute(description = "Breaker trips when threshold is reached")
	public int getExceptionThreshold() { 
		return super.getExceptionThreshold(); 
	}
	
	@Override
	@ManagedAttribute(description = "Breaker trips when threshold is reached")
	public void setExceptionThreshold(int threshold) {
		super.setExceptionThreshold(threshold);
	}
   
	@Override
	@ManagedAttribute(description = "Delay in ms before open breaker goes half-open")
	public long getTimeout() { 
		return super.getTimeout(); 
	}
		   
	@Override
	@ManagedAttribute(description = "Delay in ms before open breaker goes half open")
	public void setTimeout(long timeout) { 
		super.setTimeout(timeout);
	}
		   
	@Override
	@ManagedAttribute(description = "Breaker state (closed, open, half-open)")
	public int getExceptionCount() { 
		return super.getExceptionCount(); 
	}
   
	@Override
	@ManagedAttribute(description = "Breaker will retry circuit at or after this time")
	public long getRetryTime() { 
		return super.getRetryTime();
	}
   
	@Override
	@ManagedOperation(description = "Resets the breaker")
	public void reset() { 
		super.reset();
	}
   
	@Override
	@ManagedOperation(description = "Trips the breaker, auto-resetting after timeout")
	public void trip() { 
		super.trip();
	}
   
	@Override
	@ManagedOperation(description = "Trips the breaker without auto-resetting")
	public void tripWithoutAutoReset() { 
		super.tripWithoutAutoReset();
	}
}