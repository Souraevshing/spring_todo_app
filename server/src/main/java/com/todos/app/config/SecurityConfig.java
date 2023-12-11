package com.todos.app.config;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.todos.app.security.JwtAuthenticationEntryPoint;
import com.todos.app.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

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

        // implement Basic Authentication
        httpSecurity
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorize) -> {
                    authorize.requestMatchers("/api/v1/todos/auth/**").permitAll();
                    authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
                    authorize.anyRequest().authenticated();
                });

        // removed basic auth and only use Bearer auth
        // .httpBasic(Customizer.withDefaults());

        // handle exception if JwtAuthenticationEntryPoint throws err
        httpSecurity
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint));

        // calling addFilterBefore() by passing JwtAuthenticationFilter class reference
        // to make sure to execute this class code before SecurityConfig
        // since JwtAuthenticationFilter contains logic to create jwt token and fetch
        // username
        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

        // authorize.requestMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN");
        // authorize.requestMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN");
        // authorize.requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN");
        // authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll();

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
