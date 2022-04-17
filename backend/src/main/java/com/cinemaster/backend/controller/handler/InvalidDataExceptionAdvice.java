package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidDataExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String sqlIntegrityConstraintViolationExceptionHandler(InvalidDataException e) {
        return e.getMessage();
    }

}
