package com.fiap58.producao.infrastructure;

import com.fiap58.producao.infrastructure.domain.PedidoDb;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

public interface PedidoDbRepository extends MongoRepository<PedidoDb, String> {

    @Query("{ 'informacoesPedido.idPedido': {$eq: ?0} }")
    PedidoDb buscaIdPedido(Long id);
}
