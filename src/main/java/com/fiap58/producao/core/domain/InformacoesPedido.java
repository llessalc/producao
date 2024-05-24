package com.fiap58.producao.core.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class InformacoesPedido {

    private Long idPedido;
    private Instant dataPedido;
    private Instant estimativaPreparo;
    private Instant dataFinalizado;
    private String statusPedido;

    public InformacoesPedido() {
        dataPedido = Instant.now();
        statusPedido = Status.RECEBIDO.getStatus();
    }
}
