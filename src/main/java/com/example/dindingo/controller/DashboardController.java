package com.example.dindingo.controller;

import com.example.dindingo.model.Dashboard;
import com.example.dindingo.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<?> obterDashboard(
            @RequestParam Long usuarioId,
            @RequestParam int mes) {

        try {
            Dashboard dashboard = dashboardService.gerarDashboard(usuarioId, mes);

            return ResponseEntity.ok(dashboard);

        } catch (RuntimeException e) {
            if (e.getMessage().equals("Nenhum lancamento este mes")) {
                return ResponseEntity.ok(e.getMessage());
            }

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}