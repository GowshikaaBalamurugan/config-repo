package com.dkart.productservice.advice;


import com.dkart.productservice.dto.CustomErrorResponse;
import com.dkart.productservice.exception.CategoryAlreadyExistsException;
import com.dkart.productservice.exception.CategoryNotFoundException;
import com.dkart.productservice.exception.ProductAlreadyExistsException;
import com.dkart.productservice.exception.ProductNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ProductServiceGlobalExceptionHandler {

    private final static String CATEGORY_NOT_FOUND="PRODUCT-SERVICE:CATEGORY_NOT_FOUND:404";
    private final static String PRODUCT_NOT_FOUND="PRODUCT-SERVICE:PRODUCT_NOT_FOUND:404";
    private final static String CATEGORY_ALREADY_EXISTS="PRODUCT-SERVICE:CATEGORY_ALREADY_EXISTS:409";
    private final static String PRODUCT_ALREADY_EXISTS="PRODUCT-SERVICE:PRODUCT_ALREADY_EXISTS:409";

    private final static String BAD_REQUEST_EX="PRODUCT-SERVICE:BAD_REQUEST:400";
    private final static String CONSTRAINT_VIOLATION_EX="PRODUCT-SERVICE:CONSTRAINT_VIOLATION:400";
    private final static String METHOD_ARG_NOT_VALID_EX="PRODUCT-SERVICE:METHOD_ARG_NOT_VALID:400";
    private final static String OTHER_EX="PRODUCT-SERVICE:OTHER_EXCEPTIONS:500";



    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(CategoryNotFoundException ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(CATEGORY_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build()  ;
        log.error("ProductServiceGlobalExceptionHandler::handleCategoryNotFoundException exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundException(ProductNotFoundException ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode(PRODUCT_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build()  ;
        log.error("ProductServiceGlobalExceptionHandler::handleProductNotFoundException exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<?> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .errorCode(CATEGORY_ALREADY_EXISTS)
                .errorMessage(ex.getMessage())
                .build()  ;
        log.error("ProductServiceGlobalExceptionHandler::handleCategoryAlreadyExistsException exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<?> handleProductAlreadyExistsException(ProductAlreadyExistsException ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.CONFLICT)
                .errorCode(PRODUCT_ALREADY_EXISTS)
                .errorMessage(ex.getMessage())
                .build()  ;
        log.error("ProductServiceGlobalExceptionHandler::handleProductAlreadyExistsException exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorCode(BAD_REQUEST_EX)
                .errorMessage(ex.getMessage())
                .build()  ;
        log.error("ProductServiceGlobalExceptionHandler::handleBadRequestException exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorCode(CONSTRAINT_VIOLATION_EX)
                .errorMessage(ex.getMessage())
                .build()  ;
        log.error("ProductServiceGlobalExceptionHandler::handleConstraintViolationException exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String err=ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .errorCode(METHOD_ARG_NOT_VALID_EX)
                .errorMessage(err)
                .build();
        log.error("ProductServiceGlobalExceptionHandler::handleMethodArgumentNotValidException exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex){
        CustomErrorResponse errorResponse= CustomErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(OTHER_EX)
                .errorMessage(ex.getMessage())
                .build();
        log.error("ProductServiceGlobalExceptionHandler::handleOtherExceptions exception caught {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }



}
