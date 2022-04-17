package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.RoomAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RoomAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(RoomAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String roomAlreadyPresentExceptionHandler(RoomAlreadyPresentException e) {
        return e.getMessage();
    }

}
