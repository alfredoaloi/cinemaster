package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.EventsPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EventsPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(EventsPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String eventsPresentExceptionHandler(EventsPresentException e) {
        return e.getMessage();
    }

}
