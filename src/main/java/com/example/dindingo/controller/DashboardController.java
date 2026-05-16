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
    public ResponseEntity<Dashboard> obterDashboard(
            @RequestParam Long usuarioId,
            @RequestParam int mes) {

        Dashboard dashboard = dashboardService.gerarDashboard(usuarioId, mes);
        return ResponseEntity.ok(dashboard);
    }
}