package com.fiap58.producao.application;


import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.service.ProducaoService;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import com.fiap58.producao.infrastructure.PedidoDbRepository;
import com.fiap58.producao.infrastructure.impl.ImplConsomerApiPedidos;
import com.fiap58.producao.infrastructure.impl.PedidoDbImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pedidoProducao")
public class ApiController {

    private PedidoDbImpl pedidoDbImpl;

    private ProducaoService service;

    public ApiController(PedidoDbRepository repository, ImplConsomerApiPedidos implConsomerApiPedidos) {
        pedidoDbImpl = new PedidoDbImpl(repository);
        service = new ProducaoService(pedidoDbImpl, implConsomerApiPedidos);
    }

    @GetMapping
    public ResponseEntity<List<PedidoDb>> retornaPedidosProducao(){
        return ResponseEntity.ok().body(service.retornaPedidosProducao());
    }

    @PostMapping(value = "adicionaPedido")
    public ResponseEntity<PedidoDb> inserirPedido(@RequestBody DadosProdutosDto produtos){
        PedidoDb pedidoDb = service.inserirPedido(produtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoDb);
    }

    @PutMapping(value = "atualizarPedido/{id}")
    public ResponseEntity<PedidoDb> alteraStatusPedido(@PathVariable String id){
        PedidoDb pedidoDb = service.atualizarStatusPedido(id);
        return ResponseEntity.ok().body(pedidoDb);
    }

    @PutMapping(value = "atualizarProdutoPedido/{id}/{produtoLista}")
    public ResponseEntity<PedidoDb> alteraStatusProduto(@PathVariable String id, @PathVariable int produtoLista){
        PedidoDb pedidoDb = service.atualizarStatusProduto(id, produtoLista);
        return ResponseEntity.ok().body(pedidoDb);
    }

}
