package com.fiap58.producao.core.usecases;

import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.infrastructure.domain.PedidoDb;

import java.time.Instant;

public class AtualizarStatusPedido {

    public PedidoDb atualizaStatus(PedidoDb pedidoDb) {
        pedidoDb = verificaAtualizaStatusNulo(pedidoDb);
        String novoStatus = defineProximoStatus(pedidoDb.getInformacoesPedido().getStatusPedido());;

        pedidoDb.getInformacoesPedido()
                .setStatusPedido(novoStatus);

        pedidoDb = atualizaProdutosPeloStatusPedido(pedidoDb);
        return pedidoDb;
    }

    public PedidoDb finalizaPedido(PedidoDb pedidoDb){
        pedidoDb.getInformacoesPedido().setStatusPedido(Status.FINALIZADO.getStatus());
        pedidoDb.getInformacoesPedido().setDataFinalizado(Instant.now());
        return atualizaProdutosPeloStatusPedido(pedidoDb);
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

    private String defineProximoStatus(String status){
        if(status.equals(Status.RECEBIDO.getStatus())) {
            return Status.EM_PREPARACAO.getStatus();
        } else {
            return Status.PRONTO.getStatus();
        }
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
