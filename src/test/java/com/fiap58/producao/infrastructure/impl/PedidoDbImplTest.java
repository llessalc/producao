package com.fiap58.producao.infrastructure.impl;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.usecases.AtualizarStatusPedido;
import com.fiap58.producao.infrastructure.PedidoDbRepository;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@ExtendWith(SpringExtension.class)
class PedidoDbImplTest {

    private AutoCloseable openMocks;
    @Mock
    private PedidoDbRepository repository;

    PedidoDbImpl pedidoDbImpl;
    private List<PedidoDb> pedidoDbList = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private InformacoesPedido informacoesPedido;
    private PedidoDb pedidoDb;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        pedidoDbImpl = new PedidoDbImpl(repository);
        Produto prod1 = new Produto();
        prod1.setNome("X-Burguer Test");
        prod1.setObservacao("Sem picles");
        prod1.setQuantidade(2);
        Produto prod2 = new Produto();
        prod2.setNome("Batata Frita");
        prod2.setQuantidade(1);

        produtos.add(prod1);
        produtos.add(prod2);

        informacoesPedido = new InformacoesPedido();


        pedidoDb = new PedidoDb();
        pedidoDb.setId("1");
        pedidoDb.setProdutos(produtos);
        pedidoDb.setInformacoesPedido(informacoesPedido);

        pedidoDbList.add(pedidoDb);
    }
    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void retornaPedidos() {
        when(repository.findAll()).thenReturn(pedidoDbList);
        pedidoDbImpl.retornaPedidos();
        verify(repository, times(1)).findAll();
        assertThat(pedidoDbList.size()).isEqualTo(1);
    }

    @Test
    void retornaPedidoPorId() {
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable(pedidoDb));
        pedidoDbImpl.retornaPedidoPorId("1");
        verify(repository, times(1)).findById(anyString());
        assertThat(pedidoDb.getId()).isEqualTo("1");
    }

    @Test
    void retornaPedidoPorIdPedido() {
        when(repository.buscaIdPedido(anyLong())).thenReturn(pedidoDb);
        pedidoDbImpl.retornaPedidoPorIdPedido(1L);
        verify(repository, times(1)).buscaIdPedido(anyLong());
        assertThat(pedidoDb.getId()).isEqualTo("1");
    }

    @Test
    void salvaPedido() {
        when(repository.save(any(PedidoDb.class))).thenReturn(pedidoDb);
        pedidoDbImpl.salvaPedido(produtos, informacoesPedido);
        verify(repository, times(1)).save(any(PedidoDb.class));
        assertThat(pedidoDb.getProdutos().size()).isEqualTo(produtos.size());
        assertThat(pedidoDb.getInformacoesPedido().getStatusPedido()).isEqualTo(informacoesPedido.getStatusPedido());
    }

    @Test
    void atualizarPedido() {
        when(repository.save(any(PedidoDb.class))).thenReturn(pedidoDb);
        pedidoDbImpl.atualizarPedido(pedidoDb);
        verify(repository, times(1)).save(any(PedidoDb.class));
    }
}