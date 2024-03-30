package com.bits.bits.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found."); // a super é a classe extendida RuntimeException
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
