package com.example.dindingo.service;

import com.example.dindingo.model.Lancamentos;
import com.example.dindingo.repository.LancamentoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LaunchService {
    private final LancamentoRepository launchRepo;

    public LaunchService(LancamentoRepository launchRepo) {
        this.launchRepo = launchRepo;
    }

    public String salvar(Lancamentos lancamento) {
        try {
            String estado = valido(lancamento);
            if (estado.compareTo("sucesso")==0) {
                lancamento.setId(null);
                launchRepo.save(lancamento);
            }
            return estado;
        }
        catch (Exception e) {
            return String.format("Ocorreu um erro ao cadastrar o lancamento: %s", e);
        }
    }

    public boolean editar(Lancamentos lancamento) {
        if (lancamento.getId()==null) {
            System.out.println("id null nao permitido");
            return false;
        }

        String estado = valido(lancamento);
        if (estado.compareTo("sucesso")!=0) {
            System.out.println("[Deletar Lancamento]: Atributo(s) invalido(s)");
            return false;
        }

        long id = lancamento.getId().longValue();
        boolean lancamentoExiste = launchRepo.existsById(id);

        if (!lancamentoExiste) {
            System.out.println("[Deletar Lancamento]: ID nao encontrado");
            return false;
        }

        launchRepo.save(lancamento);

        return true;
    }

    public boolean deletar(long id) {
        Lancamentos editLancamento  = launchRepo.findById(id);

        if (editLancamento==null) {
            System.out.println("[Deletar Lancamento]: ID nao encontrado");
            return false;
        }

        launchRepo.delete(editLancamento);

        return true;
    }

    public List<Lancamentos> listar() {
        return launchRepo.findAll();
    }

    private String valido(Lancamentos lancamento) {
        if (lancamento==null) {
            return "objeto lancamento null";
        }
        if (lancamento.getValor() <= 0.0) {
            return String.format("valor invalido: %f", lancamento.getValor());
        }
        if (lancamento.getNome()==null || lancamento.getNome().isBlank() ||
            lancamento.getNome().length() > 50) {
            return String.format("nome invalido: %s", lancamento.getNome());
        }
        if (lancamento.getTipo().toLowerCase().compareTo("despesa")!=0 ||
            lancamento.getTipo().toLowerCase().compareTo("receita")!=0) {
            return String.format("tipo invalido: %s", lancamento.getTipo());
        }
        return "sucesso";
    }
}
