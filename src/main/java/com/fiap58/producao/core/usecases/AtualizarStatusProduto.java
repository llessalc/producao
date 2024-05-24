package com.fiap58.producao.core.usecases;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.infrastructure.domain.PedidoDb;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class AtualizarStatusProduto {

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



    private PedidoDb verificaAtualizaStatusNulo(PedidoDb pedidoDb){
        if(pedidoDb.getInformacoesPedido().getStatusPedido() == null) {
            pedidoDb.getInformacoesPedido().setStatusPedido(Status.RECEBIDO.getStatus());
        }

        for(int i = 0; i <pedidoDb.getProdutos().size(); i++){
            if(pedidoDb.getProdutos().get(i).getStatusProduto() == null)
                pedidoDb.getProdutos().get(i).setStatusProduto(Status.RECEBIDO.getStatus());
        }
        return pedidoDb;
    }

    private String defineProximoStatus(String status){
        if(status.equals(Status.RECEBIDO.getStatus())) {
            return Status.EM_PREPARACAO.getStatus();
        } if (status.equals(Status.EM_PREPARACAO.getStatus())|| status.equals(Status.PRONTO.getStatus())){
            return Status.PRONTO.getStatus();
        }else {
            return Status.FINALIZADO.getStatus();
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







}
