package com.fiap58.producao.infrastructure.domain;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "pedidos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDb {

    @Id
    private String id;

    private List<Produto> produtos;

    private InformacoesPedido informacoesPedido;

}
