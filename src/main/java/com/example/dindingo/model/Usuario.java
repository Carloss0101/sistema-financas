package com.example.dindingo.model;

public class Usuario {
    private String nome;
    private String cpf;
    private String email;
    private String senha;

    public Usuario(String nome, String senha, String email, String cpf) {
        this.nome = nome;
        this.senha = senha;
        this.email = email;
        this.cpf = cpf;
    }

    public Usuario() {
    }

    public void cadastrar() {
        //Verificar informações do usuário e cadastrar no banco.
    }

    public boolean validarLogin() {
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
