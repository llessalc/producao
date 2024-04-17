package com.fiap58.producao.core.usecases;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.gateway.PedidoDb;

import java.time.Duration;
import java.time.Instant;
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

    public InformacoesPedido registraPedido(DadosProdutosDto produtos) {
        for (Produto produto : produtos.produtos()) {
            produto.setStatusProduto(Status.RECEBIDO.getStatus());
        }
        return defineInformacoesPedido(produtos);
    }

    // Fazer testes para todas as possibilidades
    // CRIAR ESPECIFICACAO PARA FINALIZADO
    public PedidoDb atualizaStatus(PedidoDb pedidoDb) {
        pedidoDb = verificaAtualizaStatusNulo(pedidoDb);
        String novoStatus = defineProximoStatus(pedidoDb.getInformacoesPedido().getStatusPedido());;

        pedidoDb.getInformacoesPedido()
                .setStatusPedido(novoStatus);

        pedidoDb = atualizaProdutosPeloStatusPedido(pedidoDb);
        return pedidoDb;
    }

    private PedidoDb atualizaProdutosPeloStatusPedido(PedidoDb pedidoDb){
        int valorNovoStatus =  valorStatus(pedidoDb.getInformacoesPedido().getStatusPedido());
        int valorStatus;
        for (Produto produto : pedidoDb.getProdutos()) {
            valorStatus = valorStatus(produto.getStatusProduto());

            if(valorStatus < valorNovoStatus){
                produto.setStatusProduto(pedidoDb.getInformacoesPedido().getStatusPedido());
            }
        }
        return pedidoDb;
    }
    // Fazer testes para todas as possibilidades
    public PedidoDb atualizaStatusProduto(PedidoDb pedidoDb, int produtoLista) {
        pedidoDb = verificaAtualizaStatusNulo(pedidoDb);

        Produto produto = pedidoDb.getProdutos().get(produtoLista);
        String novoStatusProduto = defineProximoStatus(produto.getStatusProduto());
        int valorNovoStatusProduto = valorStatus(novoStatusProduto);
        pedidoDb.getProdutos().get(produtoLista).setStatusProduto(novoStatusProduto);

        boolean atualizarPedido = true;

        // Verifica se todos os produtos foram atualizados e devemos atualizar pedido
        for (Produto prod : pedidoDb.getProdutos()) {

            int valorStatusProduto = valorStatus(prod.getStatusProduto());
            if(valorStatusProduto < valorNovoStatusProduto){
                atualizarPedido = false;
            }
        }
        if(atualizarPedido){
            pedidoDb.getInformacoesPedido()
                    .setStatusPedido(novoStatusProduto);
        }
        return pedidoDb;
    }

    public PedidoDb finalizaPedido(PedidoDb pedidoDb){
        pedidoDb.getInformacoesPedido().setStatusPedido(Status.FINALIZADO.getStatus());
        pedidoDb.getInformacoesPedido().setDataFinalizado(Instant.now());
        return atualizaProdutosPeloStatusPedido(pedidoDb);
    }

    private PedidoDb verificaAtualizaStatusNulo(PedidoDb pedidoDb){
        if(pedidoDb.getInformacoesPedido().getStatusPedido() == null) {
            pedidoDb.getInformacoesPedido().setStatusPedido(Status.RECEBIDO.getStatus());
        }

        for(int i = 0; i <pedidoDb.getProdutos().size(); i++){
            if(pedidoDb.getProdutos().get(i) == null)
                pedidoDb.getProdutos().get(i).setStatusProduto(Status.RECEBIDO.getStatus());
        }
        return pedidoDb;
    }

    private String defineProximoStatus(String status){
        if(status.equals(Status.RECEBIDO.getStatus())) {
            return Status.EM_PREPARACAO.getStatus();
        } else {
            return Status.PRONTO.getStatus();
        }
    }

    private int valorStatus(String status){
        if(status.equals(Status.RECEBIDO.getStatus())){
            return 1;
        } else if(status.equals(Status.EM_PREPARACAO.getStatus())){
            return 2;
        } else if(status.equals(Status.PRONTO.getStatus())){
            return 3;
        } else if(status.equals(Status.FINALIZADO.getStatus())){
            return 4;
        }
        return 0;
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

    private InformacoesPedido defineInformacoesPedido(DadosProdutosDto produtos){
        long tempoPreparo = defineTempoEstimadoDePreparoPadrao(produtos.produtos());
        InformacoesPedido informacoesPedido = new InformacoesPedido();
        informacoesPedido.setIdPedido(produtos.idPedido());
        Duration duracaoMinutos = Duration.ofMinutes(tempoPreparo);
        informacoesPedido.setEstimativaPreparo(informacoesPedido.getDataPedido().plus(duracaoMinutos));
        return informacoesPedido;
    }



}
