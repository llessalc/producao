package com.fiap58.producao.core.domain;

public enum Status {
    RECEBIDO(1,"Recebido"),
    EM_PREPARACAO(2, "Em Preparação"),
    PRONTO(3, "Pronto"),
    FINALIZADO(4, "Finalizado");

    private final int valor;
    private final String status;

    Status(int valor, String status){
        this.valor = valor;
        this.status = status;
    }

    public int getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }
}
