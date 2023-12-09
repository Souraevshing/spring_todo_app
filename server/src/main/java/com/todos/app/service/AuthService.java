package com.todos.app.service;

import com.todos.app.dto.LoginDto;
import com.todos.app.dto.RegisterDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> registerUser(RegisterDto registerDto);
    ResponseEntity<String> loginUser(LoginDto loginDto);

}
