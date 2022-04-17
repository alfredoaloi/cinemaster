package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.CategoryAlreadyPresentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CategoryAlreadyPresentExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(CategoryAlreadyPresentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String categoryAlreadyPresentExceptionHandler(CategoryAlreadyPresentException e) {
        return e.getMessage();
    }

}
