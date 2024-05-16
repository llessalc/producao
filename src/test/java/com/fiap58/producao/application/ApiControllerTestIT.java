package com.fiap58.producao.application;

import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.utils.PedidoHelper;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class ApiControllerTestIT {

    private final String ENDPOINT_API_PRODUCAO_RETORNAR = "http://localhost:8080/pedidoProducao";
    private final String ENDPOINT_API_PRODUCAO_INSERIR = "http://localhost:8080/pedidoProducao/adicionaPedido";
    private final String ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO = "http://localhost:8080/pedidoProducao/atualizarProdutoPedido/";
    private final String ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO = "http://localhost:8080/pedidoProducao/atualizarPedido/";
    private final String ENDPOINT_API_PRODUCAO_RETIRADA_PEDIDO = "http://localhost:8080/pedidoProducao/retiradaPedido/";

    @Test
    void retornaPedidosProducao() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get(ENDPOINT_API_PRODUCAO_RETORNAR)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void inserirPedido() {
        Random random = new Random();
        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedidoId(random.nextLong(9999L));

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dadosProdutosDto)
                .when()
                .post(ENDPOINT_API_PRODUCAO_INSERIR)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("$", hasKey("id"))
                .body("$", hasKey("produtos"))
                .body("$", hasKey("informacoesPedido"));
    }

    @Test
    void alteraStatusPedido() {
        Random random = new Random();
        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedidoId(random.nextLong(9999L));
        Long idPedido = dadosProdutosDto.idPedido();
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dadosProdutosDto)
                .when()
                .post(ENDPOINT_API_PRODUCAO_INSERIR);

        String urlAtualizarPedido = ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO + idPedido;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(urlAtualizarPedido)
                .then()
                .statusCode(HttpStatus.OK.value());


    }

    @Test
    void alteraStatusProduto() {
        Random random = new Random();
        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedidoId(random.nextLong(9999L));
        Long idPedido = dadosProdutosDto.idPedido();
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dadosProdutosDto)
                .when()
                .post(ENDPOINT_API_PRODUCAO_INSERIR);

        String urlAtualizarProduto = ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO + idPedido + "/"+0;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(urlAtualizarProduto)
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}