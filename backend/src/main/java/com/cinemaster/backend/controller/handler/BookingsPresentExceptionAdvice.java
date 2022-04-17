package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.BookingsPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingsPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BookingsPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String bookingsPresentExceptionHandler(BookingsPresentException e) {
        return e.getMessage();
    }

}
