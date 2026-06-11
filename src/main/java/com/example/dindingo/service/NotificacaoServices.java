package com.example.dindingo.service;

import com.example.dindingo.model.Boleto;
import com.example.dindingo.model.Notificacao;
import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.BoletoRepository;
import com.example.dindingo.repository.NotificacaoRepository;
import com.example.dindingo.repository.UsuarioRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class NotificacaoServices {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BoletoRepository boletoRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void verificarBoletosAVencer() {
        LocalDate amanha = LocalDate.now().plusDays(1);


        List<Boleto> boletosAVencer = boletoRepository.findByPagoFalseAndVencimento(amanha);


        for (Boleto boleto : boletosAVencer) {
            Notificacao notificacao = new Notificacao();
            notificacao.setUsuario(boleto.getUsuario());


            String texto = String.format("Lembrete: O boleto '%s' no valor de R$ %.2f vence amanhã (%s). Código de Barras: %s",
                    boleto.getTitulo(),
                    boleto.getValor(),
                    boleto.getVencimento().toString(),
                    boleto.getCodigoBarras() != null ? boleto.getCodigoBarras() : "Não informado");

            notificacao.setMensagem(texto);


            enviarEmail(notificacao);
        }
    }

    public boolean enviarEmail(Notificacao notificacao) {
        try {

            Optional<Usuario> usuarioOpt =
                    usuarioRepository.findById(notificacao.getUsuario().getId());

            if (usuarioOpt.isEmpty()) {
                return false;
            }

            String emailDestino = usuarioOpt.get().getEmail();

            SimpleMailMessage email = new SimpleMailMessage();

            email.setTo(emailDestino);
            email.setSubject("Nova Notificação");
            email.setText(notificacao.getMensagem());

            mailSender.send(email);

            notificacao.setLida(false);
            notificacaoRepository.save(notificacao);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Notificacao> listarNotificacoes(Long usuarioId) {
        return notificacaoRepository.findByUsuarioId(usuarioId);
    }

    public boolean marcarComoLida(Long id) {

        Optional<Notificacao> notificacaoOpt = notificacaoRepository.findById(id);

        if (notificacaoOpt.isEmpty()) {
            return false;
        }

        Notificacao notificacao = notificacaoOpt.get();

        notificacao.setLida(true);

        notificacaoRepository.save(notificacao);

        return true;
    }

    public void enviarRelatorioPorEmail(
            String emailDestino,
            byte[] pdf,
            int mes) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true);

            helper.setTo(emailDestino);
            helper.setSubject("Relatório Financeiro");
            helper.setText("Segue em anexo o relatório financeiro do mês " + mes + ".");

            helper.addAttachment(
                    "relatorio-mes-" + mes + ".pdf",
                    new ByteArrayResource(pdf)
            );

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}