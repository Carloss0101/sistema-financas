package com.example.dindingo.service;

import com.example.dindingo.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean login(Usuario usuario) {
        if (usuario.getEmail().equals("admin@email.com") &&
                usuario.getSenha().equals("1234")) {
            return true;
        }

        return false;
    }

    public boolean cadastrar(Usuario usuario) {
        try {
            String nomeReq = usuario.getNome();
            String cpfReq = usuario.getCpf().replaceAll("\\D", ""); //Pega somente os números
            String emailReq = usuario.getEmail();
            String senhaReq = usuario.getSenha();

            if(nomeReq == null || nomeReq.isBlank() || nomeReq.length() > 50) {
                return false; //nome invalido
            }

            if(cpfReq == null || cpfReq.length() != 11) {
                return false; //cpf inválido
            }

            if (emailReq == null || !emailReq.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return false;
            }

            if(senhaReq == null || senhaReq.length() > 6) {
                return false;
            }

            return true;
        } catch (Exception err) {
            System.out.println("Ocorreu um Erro ao cadatrar Usuário: " + err);
            return false;
        }
    }
}