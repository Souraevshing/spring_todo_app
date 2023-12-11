package com.todos.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDto {
    private String _token;
    private String jwtTokenType = "Bearer";
    private String role;
}
