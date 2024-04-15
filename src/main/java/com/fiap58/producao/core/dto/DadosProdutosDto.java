package com.fiap58.producao.core.dto;

import com.fiap58.producao.core.domain.Produto;

import java.util.List;

public record DadosProdutosDto(
        List<Produto> produtos
) {
}
