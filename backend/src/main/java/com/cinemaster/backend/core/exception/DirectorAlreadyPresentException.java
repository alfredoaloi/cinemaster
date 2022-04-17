package com.cinemaster.backend.core.exception;

public class DirectorAlreadyPresentException extends RuntimeException {

    public DirectorAlreadyPresentException() {
        super("Director already present");
    }

}
