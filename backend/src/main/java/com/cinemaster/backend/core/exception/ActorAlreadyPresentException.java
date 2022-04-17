package com.cinemaster.backend.core.exception;

public class ActorAlreadyPresentException extends RuntimeException {

    public ActorAlreadyPresentException() {
        super("Actor already present");
    }

}
