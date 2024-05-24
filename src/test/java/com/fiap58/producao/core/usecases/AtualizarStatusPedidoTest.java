package com.fiap58.producao.core.usecases;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;


class AtualizarStatusPedidoTest {

    private PedidoDb pedidoDb;
    private List<Produto> produtos = new ArrayList<>();
    private InformacoesPedido informacoesPedido;

    private AtualizarStatusPedido atualizarStatusPedido;

    @BeforeEach
    void setup(){
        atualizarStatusPedido = new AtualizarStatusPedido();
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
    @DisplayName("Verifica que Produtos tem seus status atualizados junto com pedido - EM PREPARACAO")
    void atualizaStatusParaEmPreparacao() {
        this.pedidoDb.getInformacoesPedido().setStatusPedido(null);
        PedidoDb pedidoDbAtualizado = atualizarStatusPedido.atualizaStatus(this.pedidoDb);
        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
    }

    @Test
    @DisplayName("Verifica que Produtos tem seus status atualizados junto com pedido - PRONTO")
    void atualizaStatusParaPronto() {
        this.pedidoDb.getInformacoesPedido().setStatusPedido("Outro status");
        PedidoDb pedidoDbAtualizado = atualizarStatusPedido.atualizaStatus(this.pedidoDb);
        pedidoDbAtualizado = atualizarStatusPedido.atualizaStatus(pedidoDbAtualizado);
        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
    }

    @Test
    @DisplayName("Verifica que Produtos em status superiores ao do pedido não são atualizados junto ao status do pedido")
    void atualizaStatusOndeProdutoEstaMaisAvancado() {
        this.pedidoDb.getProdutos().get(0).setStatusProduto(Status.PRONTO.getStatus());
        PedidoDb pedidoDbAtualizado = atualizarStatusPedido.atualizaStatus(this.pedidoDb);

        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.EM_PREPARACAO.getStatus());
    }

    @Test
    @DisplayName("Verifica que não é possível finalizar o status")
    void atualizaStatusParaFinalizadoSemSucesso() {
        PedidoDb pedidoDbAtualizado = atualizarStatusPedido.atualizaStatus(this.pedidoDb);
        pedidoDbAtualizado = atualizarStatusPedido.atualizaStatus(pedidoDbAtualizado);
        pedidoDbAtualizado = atualizarStatusPedido.atualizaStatus(pedidoDbAtualizado);
        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isNotEqualTo(Status.FINALIZADO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.PRONTO.getStatus());
    }

    @Test
    @DisplayName("Verifica que é possível finalizar o status")
    void finalizaPedido() {
        PedidoDb pedidoDbAtualizado = atualizarStatusPedido.finalizaPedido(this.pedidoDb);

        assertThat(pedidoDbAtualizado.getInformacoesPedido().getStatusPedido())
                .isEqualTo(Status.FINALIZADO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(0).getStatusProduto())
                .isEqualTo(Status.FINALIZADO.getStatus());
        assertThat(pedidoDbAtualizado.getProdutos().get(1).getStatusProduto())
                .isEqualTo(Status.FINALIZADO.getStatus());
    }
}