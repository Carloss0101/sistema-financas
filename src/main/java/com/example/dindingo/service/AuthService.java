package com.example.dindingo.service;

import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean login(Usuario usuario) {
        if (usuario.getEmail().equals("admin@email.com") &&
                usuario.getSenha().equals("1234")) {
            return true;
        }

        return false;
    }

    public String cadastrar(Usuario usuario) {
        try {
            String nomeReq = usuario.getNome();
            String cpfReq = usuario.getCpf().replaceAll("\\D", ""); //Pega somente os números
            String emailReq = usuario.getEmail();
            String senhaReq = usuario.getSenha();

            if(nomeReq == null || nomeReq.isBlank() || nomeReq.length() > 50) {
                return "Nome inválido!"; //nome invalido
            }

            if(cpfReq == null || cpfReq.length() != 11) {
                return "Cpf inválido"; //cpf inválido
            }

            if (emailReq == null || !emailReq.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return "Email inválido";
            }

            if(senhaReq == null || senhaReq.length() > 6) {
                return "Senha inválida";
            }

            usuarioRepository.salvar(usuario);
            return null;
        } catch (Exception err) {
            System.out.println("Ocorreu um Erro ao cadatrar Usuário: " + err);
            return "Ocorreu um Erro ao cadatrar Usuário";
        }
    }
}