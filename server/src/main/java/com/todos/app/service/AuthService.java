package com.todos.app.service;

import com.todos.app.dto.JwtTokenDto;
import com.todos.app.dto.LoginDto;
import com.todos.app.dto.RegisterDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<String> registerUser(RegisterDto registerDto);

    JwtTokenDto loginUser(LoginDto loginDto);

}
