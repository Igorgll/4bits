package com.bits.bits.exceptions;

public class CannotAccessException extends RuntimeException {
    public CannotAccessException(){
        super("Forbidden.");
    }

    public CannotAccessException(String message) {
        super(message);
    }
}
