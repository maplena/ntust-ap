package com.example.ntust_ap_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 2021.11.17 Boyan Start And End All

@Configuration

// 單向 Hash 加密
public class PasswordEncoder {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
