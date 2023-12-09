package com.todos.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl {

    public static void main(String[] args) {
        //generating password using BCryptPasswordEncoder class to store password in db
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("Sk@100600"));
        System.out.println(passwordEncoder.encode("admin"));
    }

}
