package com.example.dindingo.controller;

import com.example.dindingo.model.Dashboard;
import com.example.dindingo.model.Lancamentos;
import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.UsuarioRepository;
import com.example.dindingo.service.DashboardService;
import com.example.dindingo.service.LaunchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {


    private final DashboardService dashboardService;
    private final LaunchService launchService;
    private final UsuarioRepository usuarioRepository;


    public DashboardController(DashboardService dashboardService, LaunchService launchService, UsuarioRepository usuarioRepository) {
        this.dashboardService = dashboardService;
        this.launchService = launchService;
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


            List<Lancamentos> lancamentosDoUsuario = launchService.listarPorUsuaro(usuario);


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