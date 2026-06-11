package com.example.dindingo.repository;

import com.example.dindingo.model.Boleto;
import com.example.dindingo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BoletoRepository extends JpaRepository<Boleto, Long> {

    List<Boleto> findByUsuario(Usuario usuario);

    List<Boleto> findByPagoFalseAndVencimento(LocalDate vencimento);
}