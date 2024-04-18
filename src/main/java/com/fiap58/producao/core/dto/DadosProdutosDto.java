package com.fiap58.producao.core.dto;

import com.fiap58.producao.core.domain.Produto;

import java.util.List;

public record DadosProdutosDto(
        int idPedido,
        List<Produto> produtos
) {
    public DadosProdutosDto(List<Produto> produtos){
        this(1, produtos);
    }
}
