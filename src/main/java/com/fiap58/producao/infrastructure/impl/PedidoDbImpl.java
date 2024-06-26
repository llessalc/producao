package com.fiap58.producao.infrastructure.impl;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import com.fiap58.producao.infrastructure.PedidoDbRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDbImpl {

    @Autowired
    private PedidoDbRepository repository;

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
