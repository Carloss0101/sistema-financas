package com.example.dindingo.model;

public class ImpostoDeRenda {
    private Usuario usuario;
    private int id;
    private int ano;
    private double rendaAnual;

    public ImpostoDeRenda(Usuario usuario, int id, double rendaAnual, int ano) {
        this.usuario = usuario;
        this.id = id;
        this.rendaAnual = rendaAnual;
        this.ano = ano;
    }


    public void calcularImposto() {
        //Calculo do imposto
    }

    public void gerarResumo() {
        //Gerar resumo Imposto, retornar um objeto dto do relatorio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public double getRendaAnual() {
        return rendaAnual;
    }

    public void setRendaAnual(double rendaAnual) {
        this.rendaAnual = rendaAnual;
    }
}
