package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.ActorAlreadyPresentException;
import com.cinemaster.backend.core.exception.BookingAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ActorAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ActorAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String actorAlreadyPresentExceptionHandler(ActorAlreadyPresentException e) {
        return e.getMessage();
    }

}
