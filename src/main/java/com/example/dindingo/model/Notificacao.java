package com.example.dindingo.model;

import java.time.LocalDate;

public class Notificacao {
    private int id;
    private String mensagem;
    private LocalDate data;
    private boolean lida;
    private int userID;

    public Notificacao(int userID, boolean lida, LocalDate data, String mensagem, int id) {
        this.userID = userID;
        this.lida = lida;
        this.data = data;
        this.mensagem = mensagem;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
