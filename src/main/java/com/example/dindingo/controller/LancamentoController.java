package com.example.dindingo.controller;

import com.example.dindingo.model.Lancamentos;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.dindingo.service.LaunchService;


import java.util.List;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {

    private final LaunchService lancamentoService;

    public LancamentoController(LaunchService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    @PostMapping
    public ResponseEntity<String> salvar(@RequestBody Lancamentos lancamento) {
        if (!lancamentoService.salvar(lancamento)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable long id, @RequestBody Lancamentos lancamento) {
        lancamento.setId(id);
        if (!lancamentoService.editar(lancamento)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable long id) {
        if (!lancamentoService.deletar(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    public ResponseEntity<List<Lancamentos>> listar() {
        List<Lancamentos> lista = lancamentoService.listar();
        return ResponseEntity.status(200).body(lista);
    }
}
