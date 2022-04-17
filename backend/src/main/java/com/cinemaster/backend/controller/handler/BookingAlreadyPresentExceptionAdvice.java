package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.BookingAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class BookingAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(BookingAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String bookingAlreadyPresentExceptionHandler(BookingAlreadyPresentException e) {
        return e.getMessage();
    }

}
