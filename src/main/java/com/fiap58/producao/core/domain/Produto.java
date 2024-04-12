package com.fiap58.producao.core.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Produto {

    private String nome;

    private String observacao;

    private int quantidade;

    private String statusProduto;
}
