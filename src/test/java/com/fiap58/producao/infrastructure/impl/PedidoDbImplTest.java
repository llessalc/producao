package com.fiap58.producao.infrastructure.impl;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.infrastructure.PedidoDbRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;


@DataMongoTest
@ExtendWith(SpringExtension.class)
class PedidoDbImplTest {

    @Autowired
    private PedidoDbRepository repository;

    private PedidoDbImpl pedidoDbImpl;

    private List<Produto> produtos = new ArrayList<>();
    private InformacoesPedido informacoesPedido;

    @BeforeEach
    void setup(){
        pedidoDbImpl = new PedidoDbImpl(repository);
    }

    @Test
    void retornaPedidos() {
//        Produto prod1 = new Produto();
//        prod1.setNome("X-Burguer Test");
//        prod1.setObservacao("Sem picles");
//        prod1.setQuantidade(2);
//        Produto prod2 = new Produto();
//        prod2.setNome("Batata Frita");
//        prod2.setQuantidade(1);
//
//        produtos.add(prod1);
//        produtos.add(prod2);
//
//        informacoesPedido = new InformacoesPedido();
//
//        List<PedidoDb> pedidoDbs = pedidoDbImpl.retornaPedidos();
//        assertThat(pedidoDbs.size()).isEqualTo(1);
//
//        pedidoDbImpl.salvaPedido(produtos, informacoesPedido);
//
//        pedidoDbs = pedidoDbImpl.retornaPedidos();
//        assertThat(pedidoDbs.size()).isEqualTo(2);
    }

    @Test
    void retornaPedidoPorId() {
    }

    @Test
    void retornaPedidoPorIdPedido() {
    }

    @Test
    void salvaPedido() {
    }

    @Test
    void atualizarPedido() {
    }
}