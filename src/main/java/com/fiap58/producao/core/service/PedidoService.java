package com.fiap58.producao.core.service;

import com.fiap58.producao.gateway.PedidoDb;
import com.fiap58.producao.gateway.PedidoDbRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoDbRepository repository;

    public List<PedidoDb> retornaPedidosProducao(){
        List<PedidoDb> pedidos = repository.findAll();
        System.out.println(pedidos);
        List<PedidoDb> emExecucao = pedidos.stream()
                .filter(pedido -> !pedido.getInformacoesPedido().getStatusPedido().equals("Finalizado"))
                .collect(Collectors.toList());
        System.out.println(emExecucao);
        return emExecucao;
    }

}
