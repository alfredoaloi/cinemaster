package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.UsernameAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UsernameAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String usernameAlreadyPresentExceptionHandler(UsernameAlreadyPresentException e) {
        return e.getMessage();
    }

}
