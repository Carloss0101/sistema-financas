package com.example.dindingo.controller;

import com.example.dindingo.model.Usuario;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody Usuario usuario) {
        if (usuario.getEmail().equals("admin@email.com") &&
                usuario.getSenha().equals("1234")) {
            return "Login realizado com sucesso!";
        }

        return "Email ou senha inválidos";
    }
}
