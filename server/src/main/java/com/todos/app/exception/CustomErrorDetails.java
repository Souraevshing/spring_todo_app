package com.todos.app.exception;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class CustomErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

}
