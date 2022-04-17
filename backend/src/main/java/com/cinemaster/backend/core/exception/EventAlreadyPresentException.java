package com.cinemaster.backend.core.exception;

public class EventAlreadyPresentException extends RuntimeException {

    public EventAlreadyPresentException() {
        super("Event already present");
    }

}
