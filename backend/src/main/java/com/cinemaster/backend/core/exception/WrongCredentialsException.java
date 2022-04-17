package com.cinemaster.backend.core.exception;

public class WrongCredentialsException extends RuntimeException {

    public WrongCredentialsException() {
        super("Wrong Credentials");
    }

}
