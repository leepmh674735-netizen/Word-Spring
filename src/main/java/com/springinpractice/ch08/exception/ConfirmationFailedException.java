package com.springinpractice.ch08.exception;

public class ConfirmationFailedException extends Exception {
    private static final long serialVersionUID = 1L;

    public ConfirmationFailedException() {
        super();
    }

    public ConfirmationFailedException(String message) {
        super(message);
    }
}
