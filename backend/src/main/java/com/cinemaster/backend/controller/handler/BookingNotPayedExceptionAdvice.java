package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.BookingNotPayedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingNotPayedExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BookingNotPayedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String bookingNotPayedExceptionHandler(BookingNotPayedException e) {
        return e.getMessage();
    }

}
