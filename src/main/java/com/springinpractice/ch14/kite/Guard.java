package com.springinpractice.ch14.kite;

public interface Guard {
    
	String getName ();
	
	<T> T execute(GuardCallback<T> action) throws Exception;
	
}
