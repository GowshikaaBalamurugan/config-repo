package com.dkart.productservice.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus
public class CategoryAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
