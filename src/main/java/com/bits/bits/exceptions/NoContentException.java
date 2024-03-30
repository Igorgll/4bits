package com.bits.bits.exceptions;

public class NoContentException extends RuntimeException {

    public NoContentException() {
        super("No content available.");
    }

    public NoContentException(String message) {
        super(message);
    }
}
