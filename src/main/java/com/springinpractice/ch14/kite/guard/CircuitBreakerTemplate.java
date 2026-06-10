package com.springinpractice.ch14.kite.guard;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.springinpractice.ch14.kite.AbstractGuard;

@ManagedResource
public class CircuitBreakerTemplate extends AbstractGuard {
	
	... state enum, various fields, various methods ...
	
	@ManagedAttribute(
			description = "Breaker trips when threshold is reached")
	public int getExceptionThreshold() { ... }
	
	@ManagedAttribute(
			description = "Breaker trips when threshod is reached")
	         defaultValue = "5")
   public void setExceptionThreshold(int threshod) {...}
   
   @ManagedAttribute(
		   description = "Delay in ms before open breaker goes half-open",
		   public long getTimeout() { }
		   
		   @ManagedAttribute(
				   description = "Delay in ms before open breaker goes half open",
				   defaulValue = "3000")
		   public void setTimeout(long timeout) { }
		   
		   @ManagedAttribute{
			   description = "Breaker state (closed, open, half-open)")
         public int getExceptionCount() { }
   
   @ManagedAttribute(
		   
		   description = "Breaker will repty circuit at or after this time")
   public long getRetryTime() { ...}
   
   @ManagedAttribute(description = "Rosets the breaker")
   public void reset () { ...}
   
   @ManagedAttribute(
		   description ="Types the breaker,, auto-restting after timeout")
   public void trip() { }
   package com.springinpractice.ch14.kite.guard;

   import org.springframework.jmx.export.annotation.ManagedAttribute;
   import org.springframework.jmx.export.annotation.ManagedOperation;
   import org.springframework.jmx.export.annotation.ManagedResource;
   import com.springinpractice.ch14.kite.AbstractGuard;

   @ManagedResource(description = "Circuit Breaker Guard")
   public class CircuitBreakerTemplate extends AbstractGuard {
   	
   	// ... state enum, various fields, various methods ...
   	
   	@ManagedAttribute(description = "Breaker trips when threshold is reached")
   	public int getExceptionThreshold() { 
   		// 기존 로직 반환 예시
   		return 0; 
   	}
   	
   	@ManagedAttribute(description = "Breaker trips when threshold is reached")
   	public void setExceptionThreshold(int threshold) {
   		// 기존 로직 반영
   	}
      
   	@ManagedAttribute(description = "Delay in ms before open breaker goes half-open")
   	public long getTimeout() { 
   		return 0; 
   	}
   		   
   	@ManagedAttribute(description = "Delay in ms before open breaker goes half open")
   	public void setTimeout(long timeout) { 
   		// 기존 로직 반영
   	}
   		   
   	@ManagedAttribute(description = "Breaker state (closed, open, half-open)")
   	public int getExceptionCount() { 
   		return 0; 
   	}
      
   	@ManagedAttribute(description = "Breaker will retry circuit at or after this time")
   	public long getRetryTime() { 
   		return 0;
   	}
      
   	// 값을 조회/설정하는 Attribute가 아니라 '실행'하는 액션이므로 ManagedOperation이 적절합니다.
   	@ManagedOperation(description = "Resets the breaker")
   	public void reset() { 
   		// 기존 로직 반영
   	}
      
   	@ManagedOperation(description = "Trips the breaker, auto-resetting after timeout")
   	public void trip() { 
   		// 기존 로직 반영
   	}
      
   	@ManagedOperation(description = "Trips the breaker without auto-resetting")
   	public void tripWithoutAutoReset() { 
   		// 기존 로직 반영
   	}
   }
   @ManagedAttribute(
		   description = "Trips the break without auto-resetting")
   public void tripWithoutAutoReset () { }
   
   }
					