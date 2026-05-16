package com.example.dindingo.repository;

import com.example.dindingo.model.Lancamentos;
import com.example.dindingo.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamentos, Long> {
    Lancamentos findById(long id);

    boolean existsById(long id);
}