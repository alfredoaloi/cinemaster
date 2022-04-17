package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ForbiddenExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String forbiddenExceptionHandler(ForbiddenException e) {
        return e.getMessage();
    }

}
