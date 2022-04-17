package com.cinemaster.backend.core.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException() {
        super("You are not authorized");
    }

}
