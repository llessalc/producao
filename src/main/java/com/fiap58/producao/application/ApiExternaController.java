package com.fiap58.producao.application;

import com.fiap58.producao.service.ProducaoService;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import com.fiap58.producao.infrastructure.impl.PedidoDbImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pedidoProducao")
public class ApiExternaController {

    @Autowired
    private PedidoDbImpl pedidoDbImpl;
    @Autowired
    private ProducaoService service;

    @PutMapping(value = "retiradaPedido/{id}")
    public ResponseEntity retiradaPedido(@PathVariable Long id){
        PedidoDb pedidoDb = service.retiradaPedido(id);
        if(pedidoDb == null){
            return ResponseEntity.ok().body("Problem");
        }
        return ResponseEntity.ok().body(pedidoDb);
    }
}
