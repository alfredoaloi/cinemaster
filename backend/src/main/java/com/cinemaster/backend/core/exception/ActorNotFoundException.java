package com.cinemaster.backend.core.exception;

public class ActorNotFoundException extends RuntimeException {

    public ActorNotFoundException() {
        super("Actor not found");
    }

}
