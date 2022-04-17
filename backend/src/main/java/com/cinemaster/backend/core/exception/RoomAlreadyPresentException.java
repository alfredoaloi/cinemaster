package com.cinemaster.backend.core.exception;

public class RoomAlreadyPresentException extends RuntimeException {

    public RoomAlreadyPresentException() {
        super("Room already present");
    }

}
