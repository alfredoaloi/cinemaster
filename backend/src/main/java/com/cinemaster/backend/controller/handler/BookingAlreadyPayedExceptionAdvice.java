package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.BookingAlreadyPayedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BookingAlreadyPayedExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BookingAlreadyPayedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String bookingAlreadyPayedExceptionHandler(BookingAlreadyPayedException e) {
        return e.getMessage();
    }

}
