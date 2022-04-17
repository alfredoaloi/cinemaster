package com.cinemaster.backend.controller.handler;

import com.cinemaster.backend.core.exception.CouponNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CouponNotFoundExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(CouponNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String couponNotFoundExceptionHandler(CouponNotFoundException e) {
        return e.getMessage();
    }
}
