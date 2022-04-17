package com.cinemaster.backend.core.exception;

public class BookingNotFoundException extends RuntimeException {

    public BookingNotFoundException() {
        super("Booking not found");
    }

}
