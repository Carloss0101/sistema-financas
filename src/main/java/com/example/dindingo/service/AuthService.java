package com.example.dindingo.service;

import com.example.dindingo.model.Usuario;
import com.example.dindingo.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean login(Usuario usuario) {
        String emailReq = usuario.getEmail();
        String senhaReq = usuario.getSenha();

        Usuario user = usuarioRepository.findByEmail(emailReq);

        if (user == null) {
            System.out.println("[Login] email nao encontrado.");
            return false;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean senhaCorreta = encoder.matches(senhaReq, user.getSenha());

        if (senhaCorreta) {
            System.out.println("[Login] sucesso!");
            return true;
        } else {
            System.out.println("[Login] senha incorreta.");
            return false;
        }
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

            if(cpfReq == null || cpfReq.length() != 11 || usuarioRepository.existsByCpf(cpfReq)) {
                return "Cpf inválido"; //cpf inválido
            }

            if (emailReq == null || !emailReq.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") || usuarioRepository.existsByEmail(emailReq)) {
                return "Email inválido";
            }

            if(senhaReq == null || senhaReq.length() < 6) {
                return "Senha inválida";
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String senhaHash = encoder.encode(senhaReq);

            usuario.setSenha(senhaHash); //Atualiza a senha para hash
            usuarioRepository.save(usuario);
            return null;
        } catch (Exception err) {
            System.out.println("Ocorreu um Erro ao cadatrar Usuário: " + err);
            return "Ocorreu um Erro ao cadatrar Usuário";
        }
    }
}