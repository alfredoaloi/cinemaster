package com.cinemaster.backend.core.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {
        super("Category not found");
    }

}
