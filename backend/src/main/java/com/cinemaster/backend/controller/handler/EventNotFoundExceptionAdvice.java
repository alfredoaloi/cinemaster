package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.ActorNotFoundException;
import com.cinemaster.backend.core.exception.EventNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EventNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(EventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String actorNotFoundExceptionHandler(EventNotFoundException e) {
        return e.getMessage();
    }
}
