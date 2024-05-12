package com.fiap58.producao.infrastructure.impl;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import com.fiap58.producao.infrastructure.PedidoDbRepository;
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

    public PedidoDb retornaPedidoPorIdPedido(Long id){
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
