package com.example.dindingo.model;

import java.util.Date;

public class Lancamentos {
    private double valor;
    private Date data;
    private String nome;
    private String categoria;
    private String tipo;
    private boolean recorrencia;
    private String descricao;
    private Usuario usuario;
    private int id;
    private Dashboard dashboard;

    public Lancamentos(Date data, double valor, String nome, String categoria, String tipo, boolean recorrencia, String descricao, Usuario usuario, int id, Dashboard dashboard) {
        this.data = data;
        this.valor = valor;
        this.nome = nome;
        this.categoria = categoria;
        this.tipo = tipo;
        this.recorrencia = recorrencia;
        this.descricao = descricao;
        this.usuario = usuario;
        this.id = id;
        this.dashboard = dashboard;
    }

    public void salvar() {
        //Valida os dados e salva no banco.
    }

    public void editar() {
        //Valida os dados e da um upload no banco.
    }

    public void deletar() {
        //deleta o lancamento no banco por meio do id.
    }

    public void listar() {
        //Lista o lancamento por meio do id do usuario e mes;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isRecorrencia() {
        return recorrencia;
    }

    public void setRecorrencia(boolean recorrencia) {
        this.recorrencia = recorrencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDashboard(Dashboard dashboard) {this.dashboard = dashboard;}

    public Dashboard getDashboard() {return dashboard;}
}
