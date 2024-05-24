package com.fiap58.producao.core.usecases;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class RegistrarPedidoTest {

    private List<Produto> produtos = new ArrayList<>();
    private DadosProdutosDto dadosProdutosDto;

    private RegistrarPedido registrarPedido;

    @BeforeEach
    void setup(){
        registrarPedido = new RegistrarPedido();
        Produto prod1 = new Produto();
        prod1.setNome("X-Burguer Test");
        prod1.setObservacao("Sem picles");
        prod1.setQuantidade(2);
        Produto prod2 = new Produto();
        prod2.setNome("Batata Frita");
        prod2.setQuantidade(1);

        produtos.add(prod1);
        produtos.add(prod2);

        dadosProdutosDto = new DadosProdutosDto(20L, produtos);
    }

    @Test
    void registraPedido() {

        InformacoesPedido informacoesPedido = registrarPedido.registraPedido(dadosProdutosDto);


        assertThat(informacoesPedido.getIdPedido())
                .isEqualTo(20L);
        assertThat(informacoesPedido.getStatusPedido())
                .isEqualTo(Status.RECEBIDO.getStatus());
        assertThat(produtos.get(0).getStatusProduto())
                .isEqualTo(Status.RECEBIDO.getStatus());
        assertThat(produtos.get(1).getStatusProduto())
                .isEqualTo(Status.RECEBIDO.getStatus());
    }
}