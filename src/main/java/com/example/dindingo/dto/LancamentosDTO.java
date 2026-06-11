package com.example.dindingo.dto;

import com.example.dindingo.model.Lancamentos;

import java.time.LocalDate;

public class LancamentosDTO {
    private Long id;
    private double valor;
    private LocalDate data;
    private String nome;
    private String categoria;
    private String tipo;
    private boolean recorrencia;
    private String descricao;

    public LancamentosDTO() {}

    public LancamentosDTO(Lancamentos lancamento) {
        id = lancamento.getId();
        valor = lancamento.getValor();
        data = lancamento.getData();
        nome = lancamento.getNome();
        categoria = lancamento.getCategoria();
        tipo = lancamento.getTipo();
        recorrencia = lancamento.isRecorrencia();
        descricao = lancamento.getDescricao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
}
