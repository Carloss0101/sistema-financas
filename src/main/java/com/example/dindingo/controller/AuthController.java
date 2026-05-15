package com.example.dindingo.controller;

import com.example.dindingo.model.Usuario;
import com.example.dindingo.service.AuthService;
import com.example.dindingo.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        if(authService.login(usuario)) {
            String token = jwtService.gerarToken(usuario);
            return ResponseEntity.ok(Map.of("AccessToken", token));
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
