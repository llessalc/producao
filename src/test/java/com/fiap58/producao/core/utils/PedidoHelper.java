package com.fiap58.producao.core.utils;

import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.dto.DadosProdutosDto;

import java.util.ArrayList;
import java.util.List;

public abstract class PedidoHelper {

    public static DadosProdutosDto gerarPedido(){
        List<Produto> produtoList = new ArrayList<>();
        Produto prod1 = new Produto();
        prod1.setNome("X-Burguer Test");
        prod1.setObservacao("Sem picles");
        prod1.setQuantidade(2);
        Produto prod2 = new Produto();
        prod2.setNome("Batata Frita");
        prod2.setQuantidade(1);

        produtoList.add(prod1);
        produtoList.add(prod2);

        return new DadosProdutosDto(1, produtoList);
    }

    public static DadosProdutosDto gerarPedidoId(int id){
        List<Produto> produtoList = new ArrayList<>();
        Produto prod1 = new Produto();
        prod1.setNome("X-Burguer Test");
        prod1.setObservacao("Sem picles");
        prod1.setQuantidade(2);
        Produto prod2 = new Produto();
        prod2.setNome("Batata Frita");
        prod2.setQuantidade(1);

        produtoList.add(prod1);
        produtoList.add(prod2);

        return new DadosProdutosDto(id, produtoList);
    }
}
