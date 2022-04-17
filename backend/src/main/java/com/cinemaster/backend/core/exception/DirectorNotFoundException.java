package com.cinemaster.backend.core.exception;

public class DirectorNotFoundException extends RuntimeException {

    public DirectorNotFoundException() {
        super("Director not found");
    }

}
