package com.fiap58.producao.controller;


import com.fiap58.producao.core.service.PedidoService;
import com.fiap58.producao.gateway.PedidoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidoProducao")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<PedidoDb>> retornaPedidosProducao(){
        return ResponseEntity.ok().body(service.retornaPedidosProducao());
    }
}
