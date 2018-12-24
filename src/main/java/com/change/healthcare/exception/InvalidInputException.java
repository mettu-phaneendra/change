package com.change.healthcare.exception;

public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = -4427977154729202421L;

    public InvalidInputException(final String message) {
        super(message);
    }
}