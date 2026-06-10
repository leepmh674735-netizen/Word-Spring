package com.springinpractice.ch14.kite;

public interface GuardCallback<T> {
	
	T doInGuard() throws Exception;

}
