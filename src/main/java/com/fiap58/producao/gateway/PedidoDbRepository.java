package com.fiap58.producao.gateway;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface PedidoDbRepository extends MongoRepository<PedidoDb, String> {

    @Query("{ 'informacoesPedido.idPedido': {$eq: ?0} }")
    PedidoDb buscaIdPedido(int id);
}
