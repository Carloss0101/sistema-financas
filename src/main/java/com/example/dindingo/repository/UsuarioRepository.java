package com.example.dindingo.repository;

import com.example.dindingo.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsuarioRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Usuario salvar(Usuario usuario) {
        entityManager.persist(usuario);
        return usuario;
    }
}