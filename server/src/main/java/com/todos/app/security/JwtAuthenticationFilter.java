package com.todos.app.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // get jwt token from http request
        String _token = getJwtTokenFromHttpRequest(request);

        // if jwt token is not null and is a valid token
        if (StringUtils.hasText(_token) && jwtTokenProvider.validateJwtToken(_token)) {
            // get username from jwt token
            String username = jwtTokenProvider.getUsername(_token);

            // load user based on username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // create new user with user loaded using loadUserByUsername with no credentials
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // set user details and sent to http request
            authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));

            // set jwt token to current security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // calling doFilter method to run on every request once
        filterChain.doFilter(request, response);
    }

    // getting jwt token from 'Authorization' header
    private String getJwtTokenFromHttpRequest(HttpServletRequest request) {
        // get full token from header containing `Bearer ${jwtToken}`
        String bearerToken = request.getHeader("Authorization");

        // if bearer token exists and starts with `Bearer` return the token after
        // `Bearer `, else return null
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}