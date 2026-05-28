package com.example.dindingo.controller;

import com.example.dindingo.dto.LancamentosDTO;
import com.example.dindingo.model.Lancamentos;
import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dindingo.service.LaunchService;


import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    private final LaunchService lancamentoService;
    private final UsuarioRepository userRepo;

    public LancamentoController(LaunchService lancamentoService, UsuarioRepository userRepo) {
        this.lancamentoService = lancamentoService;
        this.userRepo = userRepo;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<String> salvar(@PathVariable long userId, @RequestBody Lancamentos lancamento) {
        Usuario user = userRepo.findById(userId);
        lancamento.setUsuario(user);
        if (!lancamentoService.salvar(lancamento)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{launchId}")
    public ResponseEntity<String> editar(@PathVariable long launchId, @RequestBody Lancamentos lancamento) {
        lancamento.setId(launchId);
        if (!lancamentoService.editar(lancamento)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{launchId}")
    public ResponseEntity<String> deletar(@PathVariable long launchId) {
        if (!lancamentoService.deletar(launchId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping("/listar/{userId}")
    public ResponseEntity<List<LancamentosDTO>> listar(@PathVariable long userId) {
        List<LancamentosDTO> lista = lancamentoService.listarPorUsuario(userRepo.findById(userId));
        return ResponseEntity.status(200).body(lista);
    }
}
