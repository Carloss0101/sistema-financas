package com.example.dindingo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // desativa CSRF (API REST)
                .authorizeHttpRequests(auth -> auth
                        // Permite qualquer requisição para qualquer endpoint sem precisar de login
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}