package com.todos.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class AuthException extends RuntimeException {

    private String message;
    private HttpStatus statusCode;

}
