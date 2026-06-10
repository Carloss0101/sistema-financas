package com.example.dindingo.service;

import com.example.dindingo.model.Boleto;
import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.BoletoRepository;
import com.example.dindingo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final UsuarioRepository usuarioRepository;

    public BoletoService(BoletoRepository boletoRepository, UsuarioRepository usuarioRepository) {
        this.boletoRepository = boletoRepository;
        this.usuarioRepository = usuarioRepository;
    }


    public Boleto cadastrar(Boleto boleto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        boleto.setUsuario(usuario);


        boleto.setPago(false);


        if (boleto.getCodigoBarras() != null) {
            String codigoLimpo = boleto.getCodigoBarras().replaceAll("[^0-9]", "");
            boleto.setCodigoBarras(codigoLimpo);
        }


        return boletoRepository.save(boleto);
    }


    public List<Boleto> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return boletoRepository.findByUsuario(usuario);
    }


    public Boleto editar(Long id, Boleto dadosAtualizados) {

        Boleto boletoExistente = boletoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleto não encontrado."));


        boletoExistente.setTitulo(dadosAtualizados.getTitulo());
        boletoExistente.setValor(dadosAtualizados.getValor());
        boletoExistente.setVencimento(dadosAtualizados.getVencimento());

        if (dadosAtualizados.getCodigoBarras() != null) {
            String codigoLimpo = dadosAtualizados.getCodigoBarras().replaceAll("[^0-9]", "");
            boletoExistente.setCodigoBarras(codigoLimpo);
        }


        return boletoRepository.save(boletoExistente);
    }


    public void deletar(Long id) {
        if (!boletoRepository.existsById(id)) {
            throw new RuntimeException("Boleto não encontrado.");
        }
        boletoRepository.deleteById(id);
    }


    public Boleto marcarComoPago(Long id) {
        Boleto boleto = boletoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleto não encontrado."));


        boleto.setPago(true);

        return boletoRepository.save(boleto);
    }
}