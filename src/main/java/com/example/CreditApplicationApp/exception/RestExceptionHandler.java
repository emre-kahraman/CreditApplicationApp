package com.example.CreditApplicationApp.exception;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Exception: {} with message: {} ", exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(ApiError.builder()
                .httpStatusCode(HttpStatus.BAD_REQUEST.value())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(exception.getFieldError()
                        .getDefaultMessage())
                .dateTime(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Exception: {} with message: {} ", exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(ApiError.builder()
                .httpStatusCode(HttpStatus.METHOD_NOT_ALLOWED.value())
                .httpStatus(HttpStatus.METHOD_NOT_ALLOWED)
                .message(exception.getMessage())
                .dateTime(LocalDateTime.now())
                .build(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CreditApplicationNotFound.class)
    protected ResponseEntity<Object> handleCreditApplicationNotFoundException(CreditApplicationNotFound exception){
        log.error("Exception: {} with message: {} ", exception.getClass(), exception.getMessage());
        return new ResponseEntity<>(ApiError.builder()
                .httpStatusCode(HttpStatus.NOT_FOUND.value())
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .dateTime(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }
}
