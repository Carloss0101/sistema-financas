package com.example.dindingo.controller;

import com.example.dindingo.dto.LancamentoDTO;
import com.example.dindingo.model.Dashboard;
import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.UsuarioRepository;
import com.example.dindingo.service.DashboardService;
import com.example.dindingo.service.LancamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {


    private final DashboardService dashboardService;
    private final LancamentoService lancamentoService;
    private final UsuarioRepository usuarioRepository;


    public DashboardController(DashboardService dashboardService, LancamentoService lancamentoService, UsuarioRepository usuarioRepository) {
        this.dashboardService = dashboardService;
        this.lancamentoService = lancamentoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<?> obterDashboard(
            @RequestParam Long usuarioId,
            @RequestParam int mes) {

        try {

            Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);

            if (usuario == null) {
                return ResponseEntity.badRequest().body("Usuário não encontrado.");
            }


            List<LancamentoDTO> lancamentosDoUsuario = lancamentoService.listarPorUsuario(usuario);


            Dashboard dashboard = dashboardService.gerarDashboard(lancamentosDoUsuario, mes);

            return ResponseEntity.ok(dashboard);

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nenhum lancamento este mes")) {
                return ResponseEntity.ok(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}