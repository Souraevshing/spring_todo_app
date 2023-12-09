package com.todos.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<CustomErrorDetails> handleAuthException(AuthException authException, WebRequest request) {

        CustomErrorDetails errorDetails = new CustomErrorDetails(
                LocalDateTime.now(),
                authException.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
