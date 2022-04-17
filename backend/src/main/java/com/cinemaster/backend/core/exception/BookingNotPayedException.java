package com.cinemaster.backend.core.exception;

public class BookingNotPayedException extends RuntimeException {

    public BookingNotPayedException() {
        super("Booking not payed");
    }

}
