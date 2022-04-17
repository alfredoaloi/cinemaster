package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.EmailAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmailAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(EmailAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String emailAlreadyPresentExceptionHandler(EmailAlreadyPresentException e) {
        return e.getMessage();
    }

}
