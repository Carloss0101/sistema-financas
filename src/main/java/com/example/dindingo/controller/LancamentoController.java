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
        lancamentoService.salvar(lancamento);
        return ResponseEntity.ok("Lançamento criado com sucesso!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable int id, @RequestBody Lancamentos lancamento) {
        lancamento.setId(id);
        lancamentoService.editar(lancamento);
        return ResponseEntity.ok("Lançamento atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable int id) {
        lancamentoService.deletar(id);
        return ResponseEntity.ok("Lançamento removido com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<Lancamentos>> listar() {
        List<Lancamentos> lista = lancamentoService.listar();
        return ResponseEntity.ok(lista);
    }
}
