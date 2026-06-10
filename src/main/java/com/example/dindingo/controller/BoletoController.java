package com.example.dindingo.controller;

import com.example.dindingo.model.Boleto;
import com.example.dindingo.service.BoletoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boletos")
public class BoletoController {

    private final BoletoService boletoService;


    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    // CADASTRAR
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Boleto boleto, @RequestParam Long usuarioId) {
        try {
            Boleto novoBoleto = boletoService.cadastrar(boleto, usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoBoleto);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // LISTAR
    @GetMapping
    public ResponseEntity<?> listarPorUsuario(@RequestParam Long usuarioId) {
        try {
            List<Boleto> boletos = boletoService.listarPorUsuario(usuarioId);
            return ResponseEntity.ok(boletos);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // EDITAR
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody Boleto dadosAtualizados) {
        try {
            Boleto boletoEditado = boletoService.editar(id, dadosAtualizados);
            return ResponseEntity.ok(boletoEditado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            boletoService.deletar(id);
            return ResponseEntity.ok("Boleto deletado com sucesso.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // MARCAR COMO PAGO
    @PatchMapping("/{id}/pagar")
    public ResponseEntity<?> marcarComoPago(@PathVariable Long id) {
        try {
            Boleto boletoPago = boletoService.marcarComoPago(id);
            return ResponseEntity.ok(boletoPago);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}