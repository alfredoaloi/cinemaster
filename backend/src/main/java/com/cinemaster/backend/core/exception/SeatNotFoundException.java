package com.cinemaster.backend.core.exception;

public class SeatNotFoundException extends RuntimeException {

    public SeatNotFoundException() {
        super("Seat not found");
    }

}
