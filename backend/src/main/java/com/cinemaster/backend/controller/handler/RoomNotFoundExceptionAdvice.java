package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.RoomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoomNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String actorNotFoundExceptionHandler(RoomNotFoundException e) {
        return e.getMessage();
    }
}
