package com.fiap58.producao.infrastructure.service;

import com.fiap58.producao.infrastructure.impl.ImplConsomerApiPedidos;
import org.springframework.stereotype.Service;

@Service
public class PedidoApiService{

    ImplConsomerApiPedidos apiPedidos;

    public PedidoApiService(ImplConsomerApiPedidos implConsomerApiPedidos) {
        this.apiPedidos = implConsomerApiPedidos;
    }

    public void roolbackPedidoFinalizado(Long id) {
        apiPedidos.roolbackPedidoFinalizado(id);
    }
}
