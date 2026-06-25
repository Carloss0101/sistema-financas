package com.example.dindingo.controller;

import com.example.dindingo.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relatorio")
public class RelatorioController {
    @Autowired
    RelatorioService relatorioService;

    @GetMapping("/exportar")
    public ResponseEntity<?> exportarRelatorio(@RequestParam Long usuarioId, @RequestParam int mes) {
        try {
            byte[] pdfBytes = relatorioService.gerarPdfRelatorio(usuarioId, mes);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "relatorio-mes-" + mes + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Erro ao gerar PDF: " + e.getMessage());
        }
    }
}
