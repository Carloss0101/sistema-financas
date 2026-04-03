package com.example.dindingo.controller;

import com.example.dindingo.model.Usuario;
import com.example.dindingo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {

        if(authService.login(usuario)) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        }
        return ResponseEntity.badRequest().body("Email ou senha inválidos");
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastro(@RequestBody Usuario usuario) {
        String erro = authService.cadastrar(usuario);
        if(erro != null) {
            return ResponseEntity.badRequest().body(erro);
        }

        return ResponseEntity.ok("Cadastro realizado com sucesso!");
    }

}
