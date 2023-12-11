package com.todos.app.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDuration;

    /**
     * Generating Jwt token
     */
    public String generateJwtToken(Authentication authentication) {
        // get username
        String username = authentication.getName();

        // current date
        Date currenDate = new Date();

        // expiration date
        Date expirationDate = new Date(currenDate.getTime() + jwtExpirationDuration);

        String _token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date()).setExpiration(expirationDate)
                .signWith(key())
                .compact();

        return _token;
    }

    /**
     * decode key using HMAC-SHA algorithms
     */
    private SecretKey key() {
        return Keys
                .hmacShaKeyFor(
                        jwtSecretKey.getBytes());
    }

    /*
     * get username from jwt token payload
     */
    public String getUsername(String _token) {
        // returning the jwt body (payload) containing username
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(_token)
                .getBody();

        // getting username from claims using getSubject
        String username = claims.getSubject();

        return username;
    }

    /**
     * validate the jwt token
     */
    public boolean validateJwtToken(String _token) {
        Jwts
                .parserBuilder()
                .setSigningKey(key())
                .build()
                .parse(_token);

        return true;
    }

}
