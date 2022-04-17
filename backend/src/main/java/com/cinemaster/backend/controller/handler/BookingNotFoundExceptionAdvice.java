package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.BookingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BookingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String bookingNotFoundExceptionHandler(BookingNotFoundException e) {
        return e.getMessage();
    }
}
