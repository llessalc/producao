package com.fiap58.producao.core.controller;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.usecases.ProducaoUseCase;
import com.fiap58.producao.gateway.PedidoDb;
import com.fiap58.producao.gateway.impl.PedidoDbImpl;

import java.util.List;


public class ProducaoController {

    private PedidoDbImpl pedidoDbImpl;
    private ProducaoUseCase producaoUseCase;

    public ProducaoController(PedidoDbImpl pedidoDbImpl) {
        this.pedidoDbImpl = pedidoDbImpl;
        this.producaoUseCase = new ProducaoUseCase();
    }

    public List<PedidoDb> retornaPedidosProducao(){
        List<PedidoDb> pedidos = pedidoDbImpl.retornaPedidos();
        return producaoUseCase.pedidoEmProducao(pedidos);
    }


    public PedidoDb inserirPedido(DadosProdutosDto produtos) {
        InformacoesPedido informacoesPedido = producaoUseCase.registraPedido(produtos);
        return pedidoDbImpl.salvaPedido(produtos.produtos(), informacoesPedido);
    }

    public PedidoDb atualizarPedido(PedidoDb pedidoDb){
        return pedidoDbImpl.atualizarPedido(pedidoDb);
    }

    public PedidoDb atualizarStatusPedido(String id) {
        PedidoDb pedidoDb = pedidoDbImpl.retornaPedidoPorId(id);
        if (pedidoDb != null){
            pedidoDb = producaoUseCase.atualizaStatus(pedidoDb);
        } else {
            return null;
        }
        return atualizarPedido(pedidoDb);
    }

    public PedidoDb atualizarStatusProduto(String id, int produtoLista) {
        PedidoDb pedidoDb = pedidoDbImpl.retornaPedidoPorId(id);
        if (pedidoDb != null){
            if(produtoLista < 0 || produtoLista >= pedidoDb.getProdutos().size()){
                return null;
            }
            pedidoDb = producaoUseCase.atualizaStatusProduto(pedidoDb, produtoLista);
        } else {
            return null;
        }
        return atualizarPedido(pedidoDb);
    }

    public PedidoDb retiradaPedido(int id) {
        PedidoDb pedidoDb = pedidoDbImpl.retornaPedidoPorIdPedido(id);
        pedidoDb = producaoUseCase.finalizaPedido(pedidoDb);
        return atualizarPedido(pedidoDb);
    }
}
