package com.cinemaster.backend.core.exception;

public class BookingAlreadyPayedException extends RuntimeException {

    public BookingAlreadyPayedException() {
        super("Booking already payed");
    }

}
