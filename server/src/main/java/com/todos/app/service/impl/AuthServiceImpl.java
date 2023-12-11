package com.todos.app.service.impl;

import com.todos.app.dto.JwtTokenDto;
import com.todos.app.dto.LoginDto;
import com.todos.app.dto.RegisterDto;
import com.todos.app.entity.Role;
import com.todos.app.entity.User;
import com.todos.app.exception.AuthException;
import com.todos.app.repository.RoleRepository;
import com.todos.app.repository.UserRepository;
import com.todos.app.security.JwtTokenProvider;
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
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<String> registerUser(RegisterDto registerDto) {

        try {
            // throws exception if username already in use
            if (userRepository.existsByUsername(registerDto.getUsername())) {
                throw new AuthException("Username already exist", HttpStatus.BAD_REQUEST);
            }

            // throws exception if email already in use
            if (userRepository.existsByEmail(registerDto.getEmail())) {
                throw new AuthException("Email already exist", HttpStatus.BAD_REQUEST);
            }

            // creating new user having password encoded using BCryptEncoder
            User user = new User();
            user.setName(registerDto.getName());
            user.setUsername(registerDto.getUsername());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            // creating set of roles fetched from db, prefixed with `ROLE_USER`
            Set<Role> roles = new HashSet<>();
            Role userRoles = roleRepository.findByName("ROLE_USER");
            roles.add(userRoles);

            user.setRoles(roles);

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @Override
    public JwtTokenDto loginUser(LoginDto loginDto) {
        try {
            // getting username and password from AuthenticationManager and passing it to
            // UsernamePasswordAuthenticationToken
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getUsernameOrEmail(),
                    loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // generating jwt token
            String _token = jwtTokenProvider.generateJwtToken(authentication);

            Optional<User> usernameOrEmail = userRepository
                    .findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail());

            String userRole = null;

            // get user role from db and assign it to JwtTokenDto class
            if (usernameOrEmail.isPresent()) {
                User loggedInUser = usernameOrEmail.get();
                Optional<Role> loggedInUserRole = loggedInUser
                        .getRoles()
                        .stream()
                        .findFirst();

                if (loggedInUserRole.isPresent()) {
                    Role role = loggedInUserRole.get();
                    userRole = role.getName();
                }
            }

            JwtTokenDto jwtTokenDto = new JwtTokenDto();
            jwtTokenDto.setRole(userRole);
            jwtTokenDto.set_token(_token);

            // If authentication is successful, return jwtTokenDto class object along with
            // token, role & token type
            return jwtTokenDto;

        } catch (AuthenticationException e) {

            System.out.println(e.getMessage());

            JwtTokenDto jwtTokenDto = new JwtTokenDto();
            jwtTokenDto.setJwtTokenType(null);
            jwtTokenDto.set_token(null);
            jwtTokenDto.setRole(null);

            return jwtTokenDto;
        }

    }

}
