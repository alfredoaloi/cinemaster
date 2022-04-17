package com.cinemaster.backend.core.exception;

public class EmailAlreadyPresentException extends RuntimeException {

    public EmailAlreadyPresentException() {
        super("Email already present");
    }

}
