package com.fiap58.producao.core.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProdutoTest {

    @Test
    void verificaProdutoGettersESetters(){
        Produto produto = new Produto();
        produto.setNome("Nome");
        produto.setObservacao("Observacao");
        produto.setQuantidade(1);
        produto.setStatusProduto(Status.RECEBIDO.getStatus());

        assertThat(produto.getNome()).isEqualTo("Nome");
        assertThat(produto.getObservacao()).isEqualTo("Observacao");
        assertThat(produto.getQuantidade()).isEqualTo(1);
        assertThat(produto.getStatusProduto()).isEqualTo(Status.RECEBIDO.getStatus());

    }
}