package com.example.dindingo.service;

import com.example.dindingo.dto.LancamentoDTO;
import com.example.dindingo.model.Dashboard;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    public Dashboard gerarDashboard(List<LancamentoDTO> lancamentosDoUsuario, int mes) {

        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("Mês inválido.");
        }

        double totalReceita = 0;
        double totalDespesa = 0;
        int contador = 0;

        for (LancamentoDTO lancamento : lancamentosDoUsuario) {
            if (lancamento.getData() != null && lancamento.getData().getMonthValue() == mes) {
                contador++;

                if ("receita".equalsIgnoreCase(lancamento.getTipo())) {
                    totalReceita += lancamento.getValor();
                } else if ("despesa".equalsIgnoreCase(lancamento.getTipo())) {
                    totalDespesa += lancamento.getValor();
                }
            }
        }

        if (contador == 0) {
            throw new RuntimeException("Nenhum lancamento este mes");
        }

        double saldo = totalReceita - totalDespesa;

        return new Dashboard(mes, saldo, totalReceita, totalDespesa);
    }
}