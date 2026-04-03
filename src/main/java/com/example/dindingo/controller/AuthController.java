package com.example.dindingo.controller;

import com.example.dindingo.model.Usuario;
import com.example.dindingo.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        return authService.login(usuario);
    }
}
