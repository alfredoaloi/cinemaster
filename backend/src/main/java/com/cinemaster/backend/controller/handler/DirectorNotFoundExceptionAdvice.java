package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.DirectorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DirectorNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(DirectorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String directorNotFoundExceptionHandler(DirectorNotFoundException e) {
        return e.getMessage();
    }
}
