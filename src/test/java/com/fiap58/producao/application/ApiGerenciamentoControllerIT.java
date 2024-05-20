package com.fiap58.producao.application;

import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.utils.PedidoHelper;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

class ApiGerenciamentoControllerIT {

    private final String ENDPOINT_API_PRODUCAO_INSERIR = "http://localhost:8080/pedidoProducao/adicionaPedido";
    private final String ENDPOINT_API_PRODUCAO_RETIRADA_PEDIDO = "http://localhost:8080/pedidoProducao/retiradaPedido/";

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

        String urlFinalizarPedido = ENDPOINT_API_PRODUCAO_RETIRADA_PEDIDO + idPedido;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .put(urlFinalizarPedido)
                .then()
                .statusCode(HttpStatus.OK.value());


    }

}