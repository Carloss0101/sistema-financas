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
                        .requestMatchers("/auth/**").permitAll() // libera login e cadastro
                        .anyRequest().authenticated() // resto precisa de login
                );

        return http.build();
    }
}