package com.example.dindingo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "boleto")
public class Boleto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private double valor;
    private LocalDate vencimento;
    private boolean pago;
    private String codigoBarras;


    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


    @OneToOne
    @JoinColumn(name = "notificacao_id")
    private Notificacao notificacao;


    public Boleto() {
    }

    public Boleto(LocalDate vencimento, String titulo, double valor, boolean pago, Usuario usuario, String codigoBarras) {
        this.vencimento = vencimento;
        this.titulo = titulo;
        this.valor = valor;
        this.pago = pago;
        this.usuario = usuario;
        this.codigoBarras = codigoBarras;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDate getVencimento() { return vencimento; }
    public void setVencimento(LocalDate vencimento) { this.vencimento = vencimento; }

    public boolean isPago() { return pago; }
    public void setPago(boolean pago) { this.pago = pago; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Notificacao getNotificacao() { return notificacao; }
    public void setNotificacao(Notificacao notificacao) { this.notificacao = notificacao; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }
}