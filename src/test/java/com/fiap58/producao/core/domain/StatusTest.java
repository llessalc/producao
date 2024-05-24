package com.fiap58.producao.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StatusTest {

    @Test
    void verificaStatus(){
        assertThat(Status.FINALIZADO.getValor()).isEqualTo(4);
        assertThat(Status.PRONTO.getValor()).isEqualTo(3);
        assertThat(Status.EM_PREPARACAO.getValor()).isEqualTo(2);
        assertThat(Status.RECEBIDO.getValor()).isEqualTo(1);

        assertThat(Status.FINALIZADO.getStatus()).isEqualTo("Finalizado");
        assertThat(Status.PRONTO.getStatus()).isEqualTo("Pronto");
        assertThat(Status.EM_PREPARACAO.getStatus()).isEqualTo("Em Preparação");
        assertThat(Status.RECEBIDO.getStatus()).isEqualTo("Recebido");
    }
}