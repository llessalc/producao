package com.fiap58.producao.core.service;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.usecases.AtualizarStatusPedido;
import com.fiap58.producao.core.usecases.AtualizarStatusProduto;
import com.fiap58.producao.core.usecases.RegistrarPedido;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import com.fiap58.producao.infrastructure.impl.ImplConsomerApiPedidos;
import com.fiap58.producao.infrastructure.impl.PedidoDbImpl;
import com.fiap58.producao.infrastructure.service.PedidoApiService;

import java.util.List;
import java.util.stream.Collectors;


public class ProducaoService {

    private PedidoDbImpl pedidoDbImpl;
    private AtualizarStatusProduto atualizarStatusProduto;
    private AtualizarStatusPedido atualizarStatusPedido;
    private RegistrarPedido registraPedido;
    private PedidoApiService pedidoApiService;

    public ProducaoService(PedidoDbImpl pedidoDbImpl, ImplConsomerApiPedidos implConsomerApiPedidos) {
        this.pedidoDbImpl = pedidoDbImpl;
        this.atualizarStatusProduto = new AtualizarStatusProduto();
        this.atualizarStatusPedido = new AtualizarStatusPedido();
        this.registraPedido = new RegistrarPedido();
        this.pedidoApiService = new PedidoApiService(implConsomerApiPedidos);
    }

    public List<PedidoDb> retornaPedidosProducao(){
        List<PedidoDb> pedidos = pedidoDbImpl.retornaPedidos();
        return pedidos.stream()
                .filter(pedido -> !pedido.getInformacoesPedido().getStatusPedido().equals("Finalizado"))
                .collect(Collectors.toList());
    }


    public PedidoDb inserirPedido(DadosProdutosDto produtos) {
        InformacoesPedido informacoesPedido = registraPedido.registraPedido(produtos);
        return pedidoDbImpl.salvaPedido(produtos.produtos(), informacoesPedido);
    }

    public PedidoDb atualizarPedido(PedidoDb pedidoDb){
        return pedidoDbImpl.atualizarPedido(pedidoDb);
    }

    public PedidoDb atualizarStatusPedido(String id) {
        PedidoDb pedidoDb = pedidoDbImpl.retornaPedidoPorId(id);
        if (pedidoDb != null){
            pedidoDb = atualizarStatusPedido.atualizaStatus(pedidoDb);
        } else {
            return null;
        }
        return atualizarPedido(pedidoDb);
    }

    public PedidoDb atualizarStatusProduto(String id, int produtoLista) {
        PedidoDb pedidoDb = pedidoDbImpl.retornaPedidoPorId(id);
        if (pedidoDb != null){
            if(produtoLista < 0 || produtoLista >= pedidoDb.getProdutos().size()){
                return null;
            }
            pedidoDb = atualizarStatusProduto.atualizaStatusProduto(pedidoDb, produtoLista);
        } else {
            return null;
        }
        return atualizarPedido(pedidoDb);
    }

    public PedidoDb retiradaPedido(long id) {
        try{
            PedidoDb pedidoDb = pedidoDbImpl.retornaPedidoPorIdPedido(id);
            pedidoDb = atualizarStatusPedido.finalizaPedido(pedidoDb);
            return atualizarPedido(pedidoDb);
        } catch (Exception e){
            pedidoApiService.roolbackPedidoFinalizado(id);
            return null;
        }
    }
}
