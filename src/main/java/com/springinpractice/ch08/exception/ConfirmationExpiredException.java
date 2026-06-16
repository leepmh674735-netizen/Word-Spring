package com.springinpractice.ch08.exception;

public class ConfirmationExpiredException extends Exception {
    private static final long serialVersionUID = 1L;

    public ConfirmationExpiredException() {
        super();
    }

    public ConfirmationExpiredException(String message) {
        super(message);
    }
}
