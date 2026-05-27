package com.example.dindingo.service;

import com.example.dindingo.model.Lancamentos;
import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.LancamentoRepository;
import com.example.dindingo.repository.UsuarioRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.awt.Color;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class RelatorioServices {

    private final LancamentoRepository lancamentoRepository;
    private final UsuarioRepository usuarioRepository;
    private final NumberFormat moedaFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private final DateTimeFormatter dataFormatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatorioServices(LancamentoRepository lancamentoRepository, UsuarioRepository usuarioRepository) {
        this.lancamentoRepository = lancamentoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Lancamentos> buscarLancamentosPorUsuarioId(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
            throw new RuntimeException("Usuário não encontrado para o ID: " + usuarioId);
        }
        return lancamentoRepository.findAllByUsuario(usuario);
    }

    public byte[] gerarPdfRelatorio(Long usuarioId, int mes) {
        List<Lancamentos> todosLancamentos = buscarLancamentosPorUsuarioId(usuarioId);
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        String nomeUsuario = (usuario != null) ? usuario.getNome() : "Usuário";

        double totalReceita = 0;
        double totalDespesa = 0;

        List<Lancamentos> lancamentosDoMes = todosLancamentos.stream()
                .filter(l -> l.getData() != null && l.getData().getMonthValue() == mes)
                .toList();

        for (Lancamentos l : lancamentosDoMes) {
            if ("receita".equalsIgnoreCase(l.getTipo())) {
                totalReceita += l.getValor();
            } else if ("despesa".equalsIgnoreCase(l.getTipo())) {
                totalDespesa += l.getValor();
            }
        }

        double saldoGeral = totalReceita - totalDespesa;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, new Color(38, 14, 105)); // #260e69
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA, 12, Color.GRAY);
            Font fontLabel = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.WHITE);
            Font fontTextoOpcional = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, new Color(38, 14, 105));
            Font fontValor = FontFactory.getFont(FontFactory.HELVETICA, 11, Color.BLACK);

            Paragraph titulo = new Paragraph("Dindingo - Relatório", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(5);
            document.add(titulo);

            Paragraph info = new Paragraph("Cliente: " + nomeUsuario + " | Mês de Referência: " + mes, fontSubtitulo);
            info.setAlignment(Element.ALIGN_CENTER);
            info.setSpacingAfter(25);
            document.add(info);

            PdfPTable tabelaCards = new PdfPTable(3);
            tabelaCards.setWidthPercentage(100);

            PdfPCell cellRecHead = new PdfPCell(new Paragraph("Total Receitas", fontLabel));
            cellRecHead.setBackgroundColor(new Color(34, 197, 94)); // Verde
            cellRecHead.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellRecHead.setPadding(8);

            PdfPCell cellDespHead = new PdfPCell(new Paragraph("Total Despesas", fontLabel));
            cellDespHead.setBackgroundColor(new Color(239, 68, 68)); // Vermelho
            cellDespHead.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellDespHead.setPadding(8);

            PdfPCell cellSaldoHead = new PdfPCell(new Paragraph("Saldo Líquido", fontLabel));
            cellSaldoHead.setBackgroundColor(new Color(0, 123, 255)); // Azul
            cellSaldoHead.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellSaldoHead.setPadding(8);

            tabelaCards.addCell(cellRecHead);
            tabelaCards.addCell(cellDespHead);
            tabelaCards.addCell(cellSaldoHead);

            PdfPCell cellRecVal = new PdfPCell(new Paragraph(moedaFormat.format(totalReceita), fontValor));
            cellRecVal.setPadding(12);
            cellRecVal.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell cellDespVal = new PdfPCell(new Paragraph(moedaFormat.format(totalDespesa), fontValor));
            cellDespVal.setPadding(12);
            cellDespVal.setHorizontalAlignment(Element.ALIGN_CENTER);

            Font fontSaldoCor = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11,
                    saldoGeral >= 0 ? new Color(34, 197, 94) : new Color(239, 68, 68));
            PdfPCell cellSaldoVal = new PdfPCell(new Paragraph(moedaFormat.format(saldoGeral), fontSaldoCor));
            cellSaldoVal.setPadding(12);
            cellSaldoVal.setHorizontalAlignment(Element.ALIGN_CENTER);

            tabelaCards.addCell(cellRecVal);
            tabelaCards.addCell(cellDespVal);
            tabelaCards.addCell(cellSaldoVal);

            document.add(tabelaCards);

            Paragraph espaco = new Paragraph("", fontTextoOpcional);
            espaco.setSpacingAfter(10);
            document.add(espaco);

            PdfPTable tabelaItens = new PdfPTable(4);
            tabelaItens.setWidthPercentage(100);
            tabelaItens.setWidths(new float[]{20f, 40f, 18f, 22f});

            String[] headers = {"Data", "Descrição / Nome", "Tipo", "Valor"};
            for (String h : headers) {
                PdfPCell cellHeader = new PdfPCell(new Paragraph(h, fontLabel));
                cellHeader.setBackgroundColor(new Color(38, 14, 105));
                cellHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellHeader.setPadding(8);
                tabelaItens.addCell(cellHeader);
            }

            if (lancamentosDoMes.isEmpty()) {
                PdfPCell cellVazia = new PdfPCell(new Paragraph("Nenhum lançamento cadastrado neste mês.", fontValor));
                cellVazia.setColspan(4);
                cellVazia.setPadding(15);
                cellVazia.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabelaItens.addCell(cellVazia);
            } else {
                for (Lancamentos l : lancamentosDoMes) {
                    String dataStr = l.getData() != null ? l.getData().format(dataFormatador) : "-";
                    PdfPCell cData = new PdfPCell(new Paragraph(dataStr, fontValor));
                    cData.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cData.setPadding(6);
                    tabelaItens.addCell(cData);

                    PdfPCell cNome = new PdfPCell(new Paragraph(l.getNome(), fontValor));
                    cNome.setPadding(6);
                    tabelaItens.addCell(cNome);

                    boolean ehReceita = "receita".equalsIgnoreCase(l.getTipo());
                    Font fontTipoColor = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, ehReceita ? new Color(34, 197, 94) : new Color(239, 68, 68));
                    PdfPCell cTipo = new PdfPCell(new Paragraph(l.getTipo().toUpperCase(), fontTipoColor));
                    cTipo.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cTipo.setPadding(6);
                    tabelaItens.addCell(cTipo);

                    String sinal = ehReceita ? "" : "- ";
                    Font fontValColor = FontFactory.getFont(FontFactory.HELVETICA, 11, ehReceita ? new Color(34, 197, 94) : new Color(239, 68, 68));
                    PdfPCell cValor = new PdfPCell(new Paragraph(sinal + moedaFormat.format(l.getValor()), fontValColor));
                    cValor.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cValor.setPadding(6);
                    tabelaItens.addCell(cValor);
                }
            }

            document.add(tabelaItens);

//            Paragraph rodape = new Paragraph("\n\nGerado automaticamente pelo Sistema de Finanças Dindingo.", fontSubtitulo);
//            rodape.setAlignment(Element.ALIGN_CENTER);
//            document.add(rodape);

            document.close();
        } catch (DocumentException e) {
            System.err.println("Erro na estrutura do PDF: " + e.getMessage());
        }

        return out.toByteArray();
    }
}