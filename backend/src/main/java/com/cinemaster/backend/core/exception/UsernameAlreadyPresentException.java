package com.cinemaster.backend.core.exception;

public class UsernameAlreadyPresentException extends RuntimeException {

    public UsernameAlreadyPresentException() {
        super("Username already present");
    }

}
