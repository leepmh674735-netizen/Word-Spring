package com.springinpractice.ch14.kite.expection;

import org.springframework.core.NestedRuntimeException;

public class GuardException extends NestedRuntimeException {
	public GuardException (String msg) { super (msg); }

}
