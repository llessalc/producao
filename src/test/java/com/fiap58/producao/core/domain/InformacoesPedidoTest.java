package com.fiap58.producao.core.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InformacoesPedidoTest {

    @Test
    void verificaInformacoesPedidosGettersESetters(){
        InformacoesPedido informacoesPedido = new InformacoesPedido();
        Instant atual = Instant.now();
        informacoesPedido.setIdPedido(100L);
        informacoesPedido.setDataPedido(atual);
        informacoesPedido.setEstimativaPreparo(atual);
        informacoesPedido.setDataFinalizado(atual);
        informacoesPedido.setStatusPedido(Status.EM_PREPARACAO.getStatus());

        assertThat(informacoesPedido.getIdPedido()).isEqualTo(100L);
        assertThat(informacoesPedido.getDataPedido()).isEqualTo(atual);
        assertThat(informacoesPedido.getDataFinalizado()).isEqualTo(atual);
        assertThat(informacoesPedido.getEstimativaPreparo()).isEqualTo(atual);
        assertThat(informacoesPedido.getStatusPedido()).isEqualTo(Status.EM_PREPARACAO.getStatus());

    }

}