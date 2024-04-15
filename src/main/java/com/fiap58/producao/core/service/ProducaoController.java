package com.fiap58.producao.core.service;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
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
        InformacoesPedido informacoesPedido = producaoUseCase.registraPedido(produtos.produtos());
        return pedidoDbImpl.salvaPedido(produtos.produtos(), informacoesPedido);
    }
}
