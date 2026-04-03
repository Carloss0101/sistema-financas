package com.example.dindingo.service;

import com.example.dindingo.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public String login(Usuario usuario) {
        if (usuario.getEmail().equals("admin@email.com") &&
                usuario.getSenha().equals("1234")) {
            return "Login realizado com sucesso!";
        }

        return "Email ou senha inválidos";
    }
}