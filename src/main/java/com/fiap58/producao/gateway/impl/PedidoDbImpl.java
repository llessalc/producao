package com.fiap58.producao.gateway.impl;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.gateway.PedidoDb;
import com.fiap58.producao.gateway.PedidoDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoDbImpl {

    private final PedidoDbRepository repository;

    public PedidoDbImpl(PedidoDbRepository repository) {
        this.repository = repository;
    }

    public List<PedidoDb> retornaPedidos(){
        return repository.findAll();
    }

    public PedidoDb retornaPedidoPorId(String id){
        return repository.findById(id).orElse(null);
    }

    public PedidoDb retornaPedidoPorIdPedido(int id){
        return repository.buscaIdPedido(id);
    }

    public PedidoDb salvaPedido(List<Produto> produtos, InformacoesPedido informacoesPedido){
        PedidoDb pedidoDb = new PedidoDb(null, produtos, informacoesPedido);
        return repository.save(pedidoDb);
    }

    public PedidoDb atualizarPedido(PedidoDb pedidoDb) {

        return repository.save(pedidoDb);
    }
}
