package com.example.dindingo.model;

public class Dashboard {

    private int mes;
    private double totalReceita;
    private double totalDespesa;
    private double saldo;


    public Dashboard(int mes, double saldo, double totalReceita, double totalDespesa) {

        this.mes = mes;
        this.saldo = saldo;
        this.totalReceita = totalReceita;
        this.totalDespesa = totalDespesa;
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

}
