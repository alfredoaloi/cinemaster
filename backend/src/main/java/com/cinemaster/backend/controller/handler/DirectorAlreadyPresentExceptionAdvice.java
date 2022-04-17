package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.DirectorAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DirectorAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(DirectorAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String directorAlreadyPresentExceptionHandler(DirectorAlreadyPresentException e) {
        return e.getMessage();
    }

}
