package com.fiap58.producao.core.usecases;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.gateway.PedidoDb;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ProducaoUseCase {

    /*
    - Recebe o pedido
    - Retira pedido para preparação
    - Entrega pedido

     */

    public List<PedidoDb> pedidoEmProducao(List<PedidoDb> pedidoDbList){
        return pedidoDbList.stream()
                .filter(pedido -> !pedido.getInformacoesPedido().getStatusPedido().equals("Finalizado"))
                .collect(Collectors.toList());
    }

    public InformacoesPedido registraPedido(List<Produto> produtos) {
        for (Produto produto : produtos) {
            produto.setStatusProduto(Status.RECEBIDO.getStatus());
        }
        return defineInformacoesPedido(produtos);
    }





    private long defineTempoEstimadoDePreparoPadrao(List<Produto> produtos){
        long tempoPreparo = 0;
        // Lanche >= 3und -> 25min | < 3 -> 15min
        // Acompanhamento >= 3und -> 15min | < 3 -> 10min
        // Sobremesa >= 3 -> 15min | < 3 -> 10min

        int qtdProdutos = produtos.stream().mapToInt(produto -> produto.getQuantidade()).sum();
        int qtdVariacoes = produtos.size();

        if (qtdVariacoes > 3 || qtdProdutos > 5) {
            tempoPreparo = 60L;
        } else {
            tempoPreparo += qtdProdutos * 10L;
        }

        return tempoPreparo;
    }

    private InformacoesPedido defineInformacoesPedido(List<Produto> produtos){
        long tempoPreparo = defineTempoEstimadoDePreparoPadrao(produtos);
        InformacoesPedido informacoesPedido = new InformacoesPedido();
        Duration duracaoMinutos = Duration.ofMinutes(tempoPreparo);
        informacoesPedido.setEstimativaPreparo(informacoesPedido.getDataPedido().plus(duracaoMinutos));
        return informacoesPedido;
    }



}
