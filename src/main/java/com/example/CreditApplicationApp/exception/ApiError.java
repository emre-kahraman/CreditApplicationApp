package com.example.CreditApplicationApp.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiError {

    private int httpStatusCode;
    private HttpStatus httpStatus;
    private LocalDateTime dateTime;
    private String message;
}
