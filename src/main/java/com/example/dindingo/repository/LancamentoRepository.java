package com.example.dindingo.repository;

import com.example.dindingo.dto.LancamentoDTO;
import com.example.dindingo.model.Lancamento;
import com.example.dindingo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    Lancamento findById(long id);

    List<LancamentoDTO> findAllByUsuario(Usuario usuario);

    boolean existsById(long id);
}