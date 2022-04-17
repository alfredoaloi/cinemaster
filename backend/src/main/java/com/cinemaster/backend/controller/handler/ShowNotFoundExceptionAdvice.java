package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.ShowNotFoundException;
import com.cinemaster.backend.core.exception.WrongCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ShowNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(ShowNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String showNotFoundExceptionHandler(ShowNotFoundException e) {
        return e.getMessage();
    }
}
