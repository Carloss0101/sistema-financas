package com.example.dindingo.model;

import java.time.LocalDate;
import java.util.Date;

public class Boleto {

    private LocalDate vencimento;
    private String titulo;
    private double valor;
    private int id;
    private boolean pago;
    private Usuario usuario;
    private Notificacao notificacao;

    public Boleto(LocalDate vencimento, String titulo, double valor, int id, boolean pago, Usuario usuario, Notificacao notificacao) {
        this.vencimento = vencimento;
        this.titulo = titulo;
        this.valor = valor;
        this.id = id;
        this.pago = pago;
        this.usuario = usuario;
        this.notificacao = notificacao;
    }


    public void cadastrar() {
        //Valida os dados e salva no banco.
    }

    public void editar() {
        //Valida os dados e da um upload no banco.
    }

    public void deletar() {
        //deleta o lancamento no banco por meio do id.
    }

    public void verificarVencimento() {

    }

    public void marcarComoPago(){
    }


    public LocalDate getVencimento() {return vencimento;}

    public void setVencimento(LocalDate vencimento) {this.vencimento = vencimento;}

    public String getTitulo() {return titulo;}

    public void setTitulo(String titulo) {this.titulo = titulo;}

    public double getValor() {return valor;}

    public void setValor(double valor) {this.valor = valor;}

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public boolean isPago() {return pago;}

    public void setPago(boolean pago) {this.pago = pago;}

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public Notificacao getNotificacao() {return notificacao;}

    public void setNotificacao(Notificacao notificacao) {this.notificacao = notificacao;}
}
