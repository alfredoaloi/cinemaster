package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.DirectorAlreadyPresentException;
import com.cinemaster.backend.core.exception.EventAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EventAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(EventAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String eventAlreadyPresentExceptionHandler(EventAlreadyPresentException e) {
        return e.getMessage();
    }

}
