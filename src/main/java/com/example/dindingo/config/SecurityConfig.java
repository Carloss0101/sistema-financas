package com.example.dindingo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/cadastro").permitAll()
                        .requestMatchers("/pages/login.html", "/pages/cadastro.html").permitAll()
                        .requestMatchers("/css/auth.css", "/css/global.css", "/js/api/api.js", "/js/auth/**").permitAll()

                        .anyRequest().authenticated()
                )

                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> {
                            String acceptHeader = request.getHeader("Accept");

                            if (acceptHeader != null && acceptHeader.contains("application/json") || request.getRequestURI().startsWith("/auth") == false && !request.getRequestURI().endsWith(".html")) {
                                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                response.setContentType("application/json;charset=UTF-8");
                                response.getWriter().write("{\"erro\": \"Não autorizado. Token inválido ou ausente.\"}");
                            } else {
                                response.sendRedirect("/pages/login.html");
                            }
                        })
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}