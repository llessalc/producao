package com.fiap58.producao.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class ApiControllerTest {



    @Test
    void retornaPedidosProducao() {

    }

    @Test
    void inserirPedido() {
    }

    @Test
    void alteraStatusPedido() {
    }

    @Test
    void alteraStatusProduto() {
    }
}