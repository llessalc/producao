package com.fiap58.producao.gateway;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface PedidoDbRepository extends MongoRepository<PedidoDb, String> {
}
