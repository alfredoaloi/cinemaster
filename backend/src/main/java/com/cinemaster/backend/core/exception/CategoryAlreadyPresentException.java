package com.cinemaster.backend.core.exception;

public class CategoryAlreadyPresentException extends RuntimeException {

    public CategoryAlreadyPresentException() {
        super("Category already present");
    }

}
