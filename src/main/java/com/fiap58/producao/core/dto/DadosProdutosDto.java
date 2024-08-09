package com.fiap58.producao.core.dto;

import com.fiap58.producao.core.domain.Produto;

import java.util.List;

public record DadosProdutosDto(
        Long idPedido,
        List<Produto> produtos
) {
    @Override
    public String toString() {
        return "DadosProdutosDto{" +
                "idPedido=" + idPedido +
                ", produtos=" + produtos +
                '}';
    }

    public DadosProdutosDto(List<Produto> produtos){
        this(1L, produtos);


    }
}
