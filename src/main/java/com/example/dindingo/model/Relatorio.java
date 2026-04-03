package com.example.dindingo.model;

public class Relatorio {
    private int id;
    private Usuario usuario;
    private int mes;
    private double totalReceita;
    private double totalDespesa;
    private double saldo;
    private Notificacao notificacao;

    public Relatorio(int id, Notificacao notificacao, double saldo, double totalReceita, double totalDespesa, int mes, Usuario usuario) {
        this.id = id;
        this.notificacao = notificacao;
        this.saldo = saldo;
        this.totalReceita = totalReceita;
        this.totalDespesa = totalDespesa;
        this.mes = mes;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getTotalDespesa() {
        return totalDespesa;
    }

    public void setTotalDespesa(double totalDespesa) {
        this.totalDespesa = totalDespesa;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getTotalReceita() {
        return totalReceita;
    }

    public void setTotalReceita(double totalReceita) {
        this.totalReceita = totalReceita;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
