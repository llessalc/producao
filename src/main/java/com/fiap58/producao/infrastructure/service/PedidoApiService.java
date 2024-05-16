package com.fiap58.producao.infrastructure.service;

import com.fiap58.producao.infrastructure.impl.ImplConsomerApiPedidos;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PedidoApiService{

    @Autowired
    private ImplConsomerApiPedidos apiPedidos;

    public void roolbackPedidoFinalizado(Long id) {
        apiPedidos.roolbackPedidoFinalizado(id);
    }
}
