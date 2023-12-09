package com.todos.app.config;

import com.todos.app.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    // creating hashed password using bcrypt
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(20);
    }

    // calling getAuthenticationManager of AuthenticationManager interface will call
    // DAOAuthenticationManager, and it will then call loadByUsername() method to
    // find username
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    // basic authentication secure rest apis
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/v1/todos/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.anyRequest().authenticated();
                })
                // authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
                // authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
                // authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
                // authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    // in-memory authentication (database level authentication)
    // @Bean
    // public UserDetailsService userDetails() {
    // UserDetails admin = User
    // .builder()
    // .username("admin")
    // .password(passwordEncoder().encode("admin"))
    // .roles("ADMIN")
    // .build();
    //
    // UserDetails user = User
    // .builder()
    // .username("testuser")
    // .password(passwordEncoder().encode("Sk@100600"))
    // .roles("USER")
    // .build();
    //
    // return new InMemoryUserDetailsManager(admin, user);
    // }

}
