package com.example.dindingo.service;

import com.example.dindingo.model.Notificacao;
import com.example.dindingo.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificacaoServices {
    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public boolean enviarEmail(Notificacao notificacao) {
        try {

            System.out.println("Enviando email...");
            System.out.println("Mensagem: " + notificacao.getMensagem());
            System.out.println("Data: " + notificacao.getData());
            //Conectar sistema de gerar email...

            notificacao.setLida(false);
            notificacaoRepository.save(notificacao);

            return true;
        } catch (Exception e) {
            System.out.println("Erro ao enviar email: " + e.getMessage());
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
}
