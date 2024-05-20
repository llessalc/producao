package com.fiap58.producao.core.usecases;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AtualizarStatusProdutoTest {

    private PedidoDb pedidoDb;
    private List<Produto> produtos = new ArrayList<>();
    private InformacoesPedido informacoesPedido;

    private AtualizarStatusProduto atualizarStatusProduto;


    @BeforeEach
    void setup(){
        atualizarStatusProduto = new AtualizarStatusProduto();
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
    }

    @Test
    @DisplayName("Verifica que quando apenas um produto é atualizado o pedido não altera status")
    void atualizaStatusProduto() {
        this.pedidoDb.getInformacoesPedido().setStatusPedido(null);
        PedidoDb pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);

        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.RECEBIDO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.RECEBIDO.getStatus());
    }

    @Test
    @DisplayName("Verifica que quando todos os produtos são atualizados o pedido altera status")
    void atualizaStatusTodosProdutos() {
        PedidoDb pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 1);

        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
    }

    @Test
    @DisplayName("Verifica que quando todos os produtos são atualizados o pedido altera status - Pronto")
    void atualizaStatusTodosProdutosParaPronto() {
        PedidoDb pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 1);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);

        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());

        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 1);
        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
    }

    @Test
    @DisplayName("Verifica que não podemos finalizar produtos e pedidos - Pronto")
    void atualizaStatusTodosProdutosSomenteAteStatusPronto() {
        PedidoDb pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 1);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 1);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);
        pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 1);

        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
    }

    @Test
    void tentaAtualizarStatusFinalizado(){
        pedidoDb.getInformacoesPedido().setStatusPedido(Status.FINALIZADO.getStatus());
        pedidoDb.getProdutos().get(0).setStatusProduto(Status.FINALIZADO.getStatus());
        pedidoDb.getProdutos().get(1).setStatusProduto(Status.FINALIZADO.getStatus());
        PedidoDb pedidoDbAtualizado = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, 0);
        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.FINALIZADO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.FINALIZADO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.FINALIZADO.getStatus());
    }
}