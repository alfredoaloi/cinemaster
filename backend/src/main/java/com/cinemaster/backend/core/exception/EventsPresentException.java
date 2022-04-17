package com.cinemaster.backend.core.exception;

public class EventsPresentException extends RuntimeException {

    public EventsPresentException() {
        super("Events present");
    }

}
