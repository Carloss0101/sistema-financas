package com.example.dindingo.controller;

import com.example.dindingo.model.Notificacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dindingo.service.NotificacaoServices;

import java.util.List;


@RestController
@RequestMapping("/api/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoServices notificacaoServices;

    @GetMapping("/listar")
    public ResponseEntity<List<Notificacao>> listar() {
        //trazer o id do usuário pelo middware de autenticacao, para garantir que ele receba somente as notificacoes dele.
        List<Notificacao> notificacoes = notificacaoServices.listarNotificacoes(1234L);
        return ResponseEntity.ok(notificacoes);
    }

    @PatchMapping("/{id}/lida")
    public ResponseEntity<String> marcarComoLida(@PathVariable Long id) {
        boolean sucesso = notificacaoServices.marcarComoLida(id);
        if (!sucesso) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok("Notificação marcada como lida");
    }
}
