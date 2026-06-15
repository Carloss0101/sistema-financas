package com.example.dindingo.service;

import com.example.dindingo.dto.LancamentoDTO;
import com.example.dindingo.model.Lancamento;
import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LancamentoService {
    private final LancamentoRepository launchRepo;

    public LancamentoService(LancamentoRepository launchRepo) {
        this.launchRepo = launchRepo;
    }

    public boolean salvar(Lancamento lancamento) {
        try {
            String estado = validar(lancamento);
            if (estado.compareTo("sucesso")!=0) {
                System.err.println("LaunchService -> [salvar]: " + estado);
                return false;
            }
            lancamento.setId(null);
            launchRepo.save(lancamento);
            return true;
        }
        catch (Exception e) {
            System.err.println("LaunchService -> [salvar]: " + e);
            return false;
        }
    }

    public boolean editar(Lancamento lancamento) {
        String estado = validar(lancamento);
        if (estado.compareTo("sucesso")!=0) {
            System.err.println("LaunchService -> [editar]: " + estado);
            return false;
        }

        if (lancamento.getId()==null) {
            System.err.println("LaunchService -> [editar]: ID nao foi informado");
            return false;
        }

        boolean lancamentoExiste = launchRepo.existsById(lancamento.getId());

        if (!lancamentoExiste) {
            System.err.println("LaunchService -> [editar]: ID nao encontrado");
            return false;
        }

        launchRepo.save(lancamento);

        return true;
    }

    public boolean deletar(long id) {
        Lancamento delLanc  = launchRepo.findById(id);

        if (delLanc==null) {
            System.err.println("LaunchService -> [deletar]: ID nao encontrado");
            return false;
        }

        launchRepo.delete(delLanc);

        return true;
    }

    public List<LancamentoDTO> listarPorUsuario(Usuario usuario) {
        return launchRepo.findAllByUsuario(usuario);
    }

    private String validar(Lancamento lancamento) {
        if (lancamento==null) {
            return "null object";
        }
        if (lancamento.getUsuario()==null) {
            return "usuario invalido: null";
        }
        if (lancamento.getValor() <= 0.0) {
            return String.format("valor invalido: %f", lancamento.getValor());
        }
        if (lancamento.getNome()==null || lancamento.getNome().isBlank() ||
                lancamento.getNome().length() > 50) {
            return String.format("nome invalido: %s", lancamento.getNome());
        }
        if (!lancamento.getTipo().equalsIgnoreCase("despesa") &&
                !lancamento.getTipo().equalsIgnoreCase("receita")) {
            return String.format("tipo invalido: %s", lancamento.getTipo());
        }
        return "sucesso";
    }
}