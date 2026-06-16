package com.springinpractice.ch14.kite.expection;

public class CircuitOpenException extends GuardException {
	public CircuitOpenException() {
		super("Circuit open");
	}
}
