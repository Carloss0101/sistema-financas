package com.example.dindingo.model;

public class Dashboard {
    private int id;
    private Usuario usuario;
    private int mes;
    private double totalReceita;
    private double totalDespesa;
    private double saldo;
    private Notificacao notificacao;

    public Dashboard(int id, Notificacao notificacao, int mes, Usuario usuario, double saldo, double totalReceita, double totalDespesa) {
        this.id = id;
        this.notificacao = notificacao;
        this.mes = mes;
        this.usuario = usuario;
        this.saldo = saldo;
        this.totalReceita = totalReceita;
        this.totalDespesa = totalDespesa;
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

    public double getTotalReceita() {
        return totalReceita;
    }

    public void setTotalReceita(double totalReceita) {
        this.totalReceita = totalReceita;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
