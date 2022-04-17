package com.cinemaster.backend.core.exception;

public class ShowNotFoundException extends RuntimeException {

    public ShowNotFoundException() {
        super("Show not found");
    }

}
