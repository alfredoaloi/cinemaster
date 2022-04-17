package com.cinemaster.backend.core.exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException() {
        super("Event not found");
    }

}
