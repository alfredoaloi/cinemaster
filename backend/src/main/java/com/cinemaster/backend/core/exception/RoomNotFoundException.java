package com.cinemaster.backend.core.exception;

public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException() {
        super("Room not found");
    }

}
