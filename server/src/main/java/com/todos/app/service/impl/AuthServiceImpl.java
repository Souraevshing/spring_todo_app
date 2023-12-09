package com.todos.app.service.impl;

import com.todos.app.dto.LoginDto;
import com.todos.app.dto.RegisterDto;
import com.todos.app.entity.Role;
import com.todos.app.entity.User;
import com.todos.app.exception.AuthException;
import com.todos.app.repository.RoleRepository;
import com.todos.app.repository.UserRepository;
import com.todos.app.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<String> registerUser(RegisterDto registerDto) {
        try {
            //throws exception if username already in use
            if(userRepository.existsByUsername(registerDto.getUsername())) {
                throw new AuthException("Username already exist", HttpStatus.BAD_REQUEST);
            }

            //throws exception if email already in use
            if(userRepository.existsByEmail(registerDto.getEmail())) {
                throw new AuthException("Email already exist", HttpStatus.BAD_REQUEST);
            }

            //creating new user having password encoded using BCryptEncoder
            User user = new User();
            user.setName(registerDto.getName());
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            //creating set of roles fetched from db, prefixed with `ROLE_USER`
            Set<Role> roles = new HashSet<>();
            Role userRoles = roleRepository.findByName("ROLE_USER");
            roles.add(userRoles);

            user.setRoles(roles);

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @Override
    public ResponseEntity<String> loginUser(LoginDto loginDto) {
        try {
            //getting username and password from AuthenticationManager and passing it to UsernamePasswordAuthenticationToken
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(),
                    loginDto.getPassword()
            ));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // If authentication is successful, return 200 OK
            return ResponseEntity.ok("Login successful");
        } catch (AuthenticationException e) {
            // If authentication fails, return 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");
        }
    }

}
