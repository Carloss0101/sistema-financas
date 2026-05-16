package com.example.dindingo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "lancamento")
public class Lancamentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valor;
    private LocalDate data;
    private String nome;
    private String categoria;
    private String tipo;
    private boolean recorrencia;
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    //private Dashboard dashboard;

    public Lancamentos(LocalDate data, double valor, String nome, String categoria, String tipo, boolean recorrencia, String descricao, Usuario usuario, Long id) {
        this.data = data;
        this.valor = valor;
        this.nome = nome;
        this.categoria = categoria;
        this.tipo = tipo;
        this.recorrencia = recorrencia;
        this.descricao = descricao;
        this.usuario = usuario;
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
