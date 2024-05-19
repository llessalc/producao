package com.fiap58.producao.service;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.usecases.AtualizarStatusPedido;
import com.fiap58.producao.core.usecases.AtualizarStatusProduto;
import com.fiap58.producao.core.usecases.RegistrarPedido;
import com.fiap58.producao.core.utils.PedidoHelper;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import com.fiap58.producao.infrastructure.impl.PedidoDbImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProducaoServiceTest {

    @Mock
    private PedidoDbImpl pedidoDbImpl;
    @Mock
    private AtualizarStatusProduto atualizarStatusProduto;
    @Mock
    private AtualizarStatusPedido atualizarStatusPedido;
    @Mock
    private RegistrarPedido registraPedido;

    ProducaoService producaoService;

    AutoCloseable openMocks;

    private List<PedidoDb> pedidoDbList = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private InformacoesPedido informacoesPedido;
    private InformacoesPedido informacoesPedido2;
    private PedidoDb pedidoDb;
    private PedidoDb pedidoDb2;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        producaoService = new ProducaoService(pedidoDbImpl, atualizarStatusProduto,
                atualizarStatusPedido, registraPedido);


        Produto prod1 = new Produto();
        prod1.setNome("X-Burguer Test");
        prod1.setObservacao("Sem picles");
        prod1.setQuantidade(2);
        prod1.setStatusProduto(Status.RECEBIDO.getStatus());
        Produto prod2 = new Produto();
        prod2.setNome("Batata Frita");
        prod2.setQuantidade(1);
        prod2.setStatusProduto(Status.RECEBIDO.getStatus());
        produtos.add(prod1);
        produtos.add(prod2);

        informacoesPedido = new InformacoesPedido();

        informacoesPedido2 = new InformacoesPedido();
        informacoesPedido2.setStatusPedido(Status.FINALIZADO.getStatus());



        pedidoDb = new PedidoDb();
        pedidoDb.setId("10000");
        pedidoDb.setProdutos(produtos);
        pedidoDb.setInformacoesPedido(informacoesPedido);

        pedidoDb2 = new PedidoDb();
        pedidoDb2.setId("2");
        pedidoDb2.setProdutos(produtos);
        pedidoDb2.setInformacoesPedido(informacoesPedido2);

        pedidoDb2.getProdutos().get(0).setStatusProduto(Status.EM_PREPARACAO.getStatus());


        pedidoDbList.add(pedidoDb);
        pedidoDbList.add(pedidoDb2);


    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void retornaPedidosProducao() {
        when(pedidoDbImpl.retornaPedidos()).thenReturn(pedidoDbList);

        List<PedidoDb> pedidoDbListRetornada = producaoService.retornaPedidosProducao();
        verify(pedidoDbImpl, times(1)).retornaPedidos();
        assertThat(pedidoDbListRetornada.size()).isEqualTo(1);
        assertThat(pedidoDbListRetornada.get(0).getId()).isEqualTo("10000");

    }

    @Test
    void inserirPedido() {
        when(registraPedido.registraPedido(any(DadosProdutosDto.class))).thenReturn(informacoesPedido);
        when(pedidoDbImpl.salvaPedido(anyList(), any(InformacoesPedido.class)))
        .thenReturn(pedidoDb);
        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedido();
        PedidoDb pedidoDbInserido = producaoService.inserirPedido(dadosProdutosDto);

        verify(pedidoDbImpl, times(1)).salvaPedido(anyList(), any(InformacoesPedido.class));
        verify(registraPedido, times(1)).registraPedido(any(DadosProdutosDto.class));
        assertThat(pedidoDbInserido.getId()).isEqualTo("10000");
    }

    @Test
    void atualizarPedido() {
        when(pedidoDbImpl.atualizarPedido(any(PedidoDb.class))).thenReturn(pedidoDb2);

        PedidoDb pedidoDbAtualizado = producaoService.atualizarPedido(pedidoDb);

        verify(pedidoDbImpl, times(1)).atualizarPedido(any(PedidoDb.class));
        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.FINALIZADO.getStatus());

    }

    @Test
    void atualizarStatusPedido() {
        when(pedidoDbImpl.retornaPedidoPorId(anyString())).thenReturn(pedidoDb);
        when(atualizarStatusPedido.atualizaStatus(any(PedidoDb.class))).thenReturn(pedidoDb2);
        when(pedidoDbImpl.atualizarPedido(any(PedidoDb.class))).thenReturn(pedidoDb2);

        PedidoDb pedidoAtualizado = producaoService.atualizarStatusPedido("idPedido");
        assertThat(pedidoAtualizado.getInformacoesPedido().getStatusPedido()).isEqualTo(Status.FINALIZADO.getStatus());
        verify(atualizarStatusPedido, times(1)).atualizaStatus(any(PedidoDb.class));

    }

    @Test
    void atualizarStatusProduto() {
        when(pedidoDbImpl.retornaPedidoPorId(anyString())).thenReturn(pedidoDb);
        when(atualizarStatusProduto.atualizaStatusProduto(any(PedidoDb.class), anyInt())).thenReturn(pedidoDb2);
        when(pedidoDbImpl.atualizarPedido(any(PedidoDb.class))).thenReturn(pedidoDb2);

        PedidoDb pedidoAtualizado = producaoService.atualizarStatusProduto("idPedido", 1);
        verify(atualizarStatusProduto, times(1)).atualizaStatusProduto(any(PedidoDb.class), anyInt());
        assertThat(pedidoAtualizado.getProdutos().get(0).getStatusProduto()).isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoAtualizado.getProdutos().get(1).getStatusProduto()).isEqualTo(Status.RECEBIDO.getStatus());

    }

    @Test
    void retiradaPedido() {
        when(pedidoDbImpl.retornaPedidoPorIdPedido(anyLong())).thenReturn(pedidoDb);
        when(atualizarStatusPedido.finalizaPedido(any(PedidoDb.class))).thenReturn(pedidoDb2);
        when(pedidoDbImpl.atualizarPedido(any(PedidoDb.class))).thenReturn(pedidoDb2);

        PedidoDb pedidoFinalizado = producaoService.retiradaPedido(1L);
        assertThat(pedidoFinalizado.getInformacoesPedido().getStatusPedido()).isEqualTo(Status.FINALIZADO.getStatus());
        verify(atualizarStatusPedido, times(1)).finalizaPedido(any(PedidoDb.class));

    }
}