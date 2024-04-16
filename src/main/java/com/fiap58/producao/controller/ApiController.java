package com.fiap58.producao.controller;


import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.controller.ProducaoController;
import com.fiap58.producao.gateway.PedidoDb;
import com.fiap58.producao.gateway.PedidoDbRepository;
import com.fiap58.producao.gateway.impl.PedidoDbImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidoProducao")
public class ApiController {

    private PedidoDbImpl pedidoDbImpl;

    private ProducaoController service;

    public ApiController(PedidoDbRepository repository) {
        pedidoDbImpl = new PedidoDbImpl(repository);
        service = new ProducaoController(pedidoDbImpl);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDb>> retornaPedidosProducao(){
        return ResponseEntity.ok().body(service.retornaPedidosProducao());
    }

    @PostMapping(value = "adicionaPedido")
    public ResponseEntity<PedidoDb> inserirPedido(@RequestBody DadosProdutosDto produtos){
        PedidoDb pedidoDb = service.inserirPedido(produtos);
        return ResponseEntity.ok().body(pedidoDb);
    }

    @PatchMapping(value = "atualizarPedido/{id}")
    public ResponseEntity<PedidoDb> alteraStatusPedido(@PathVariable String id){
        PedidoDb pedidoDb = service.atualizarStatusPedido(id);
        return ResponseEntity.ok().body(pedidoDb);
    }

    @PatchMapping(value = "atualizarProdutoPedido/{id}/{produtoLista}")
    public ResponseEntity<PedidoDb> alteraStatusProduto(@PathVariable String id, @PathVariable int produtoLista){
        PedidoDb pedidoDb = service.atualizarStatusProduto(id, produtoLista);
        return ResponseEntity.ok().body(pedidoDb);
    }


}
