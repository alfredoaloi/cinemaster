package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.SeatNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SeatNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(SeatNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String seatNotFoundExceptionHandler(SeatNotFoundException e) {
        return e.getMessage();
    }
}
