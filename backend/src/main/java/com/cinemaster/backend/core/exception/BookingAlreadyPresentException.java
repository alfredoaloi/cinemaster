package com.cinemaster.backend.core.exception;

public class BookingAlreadyPresentException extends RuntimeException {

    public BookingAlreadyPresentException() {
        super("Booking already present");
    }

}
