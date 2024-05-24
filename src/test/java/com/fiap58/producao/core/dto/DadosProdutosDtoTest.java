package com.fiap58.producao.core.dto;

import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class DadosProdutosDtoTest {

    @Test
    void verificaDadosProdutoDto() {
        Produto produto = new Produto();
        produto.setNome("Nome");
        produto.setObservacao("Observacao");
        produto.setQuantidade(1);
        produto.setStatusProduto(Status.RECEBIDO.getStatus());
        List<Produto> produtoList = new ArrayList<>();
        produtoList.add(produto);


        DadosProdutosDto dadosProdutosDto = new DadosProdutosDto(produtoList);

        assertThat(dadosProdutosDto.idPedido()).isEqualTo(1L);
        assertThat(dadosProdutosDto.produtos()).isEqualTo(produtoList);
    }

}