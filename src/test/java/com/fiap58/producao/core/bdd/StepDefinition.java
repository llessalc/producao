package com.fiap58.producao.core.bdd;

import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.utils.PedidoHelper;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinition {

    private Response response;
    private PedidoDb pedidoDbResposta;
    private final String ENDPOINT_API_PRODUCAO_INSERIR = "http://localhost:8080/pedidoProducao/adicionaPedido";
    private final String ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO = "http://localhost:8080/pedidoProducao/atualizarProdutoPedido/";
    private final String ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO = "http://localhost:8080/pedidoProducao/atualizarPedido/";
    private final String ENDPOINT_API_PRODUCAO_RETIRADA_PEDIDO = "http://localhost:8080/pedidoProducao/retiradaPedido/";

    //Receber um pedido e listar
    @Quando("submeter um novo pedido")
    public PedidoDb submeter_um_novo_pedido() {
        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedido();
        response = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dadosProdutosDto)
                    .when().post(ENDPOINT_API_PRODUCAO_INSERIR);
        return response.then().extract().as(PedidoDb.class);
    }

    @Então("o pedido é incluido com sucesso")
    public void pedido_incluido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("./schemas/PedidoDb.json"));

        // Write code here that turns the phrase above into concrete actions
    }
    @Dado("que temos um pedido com mais de um produto no mesmo estado")
    public void que_temos_um_pedido_com_mais_de_um_produto_no_mesmo_estado() {
        pedidoDbResposta = submeter_um_novo_pedido();

    }

    @Quando("atualizar o status de apenas um produto")
    public PedidoDb atualizar_o_status_de_apenas_um_produto() {
        String id = pedidoDbResposta.getId();
        String url = ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO + id + '/' + 0;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(url);
        return response.then().extract().as(PedidoDb.class);
    }
    @Então("o status do pedido não é alterado")
    public void o_status_do_pedido_nao_eh_alterado() {
        String statusPedidoInicial = pedidoDbResposta.getInformacoesPedido().getStatusPedido();
        String statusProdutoAlteradoInicial = pedidoDbResposta.getProdutos().get(0).getStatusProduto();
        String statusPedidoAposAttProd = response.then().extract().as(PedidoDb.class)
                .getInformacoesPedido().getStatusPedido();
        String statusProdutoAposAttProd = response.then().extract().as(PedidoDb.class)
                .getProdutos().get(0).getStatusProduto();

        assertThat(statusPedidoAposAttProd).isEqualTo(statusPedidoInicial);
        assertThat(statusProdutoAposAttProd).isNotEqualTo(statusProdutoAlteradoInicial);
    }

    @Dado("que temos um pedido com mais de um produto em status diferentes")
    public void que_temos_um_pedido_com_mais_de_um_produto_em_status_diferentes() {
        // Cria pedido
        pedidoDbResposta = submeter_um_novo_pedido();
        // Atualiza um dos pedidos
        pedidoDbResposta = atualizar_o_status_de_apenas_um_produto();
    }

    @Quando("atualizar um produto com status inferior aos demais")
    public void atualizar_um_produto_com_status_inferior_aos_demais() {
        String id = pedidoDbResposta.getId();
        String url = ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO + id + '/' + 1;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(url);
    }

    @Então("o status do pedido é atualizado para o mesmo status do produto atualizado")
    public void o_status_do_pedido_eh_atualizado_para_o_mesmo_status_do_produto_atualizado() {
        String statusPedidoInicial = pedidoDbResposta.getInformacoesPedido().getStatusPedido();
        String statusProdutoAlteradoInicial = pedidoDbResposta.getProdutos().get(1).getStatusProduto();
        String statusPedidoAposAttProd = response.then().extract().as(PedidoDb.class)
                .getInformacoesPedido().getStatusPedido();
        String statusProdutoAposAttProd = response.then().extract().as(PedidoDb.class)
                .getProdutos().get(1).getStatusProduto();

        assertThat(statusPedidoAposAttProd).isNotEqualTo(statusPedidoInicial);
        assertThat(statusPedidoAposAttProd).isEqualTo(statusProdutoAposAttProd);
        assertThat(statusProdutoAposAttProd).isNotEqualTo(statusProdutoAlteradoInicial);
    }

    @Quando("atualizar o status do pedido")
    public void atualizar_o_status_do_pedido() {
        // Cria pedido
        pedidoDbResposta = submeter_um_novo_pedido();
        String id = pedidoDbResposta.getId();
        String url = ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO + id;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(url);
    }

    @Então("o status dos produtos com status inferior também são atualizados")
    public void o_status_dos_produtos_com_status_inferior_tambem_sao_atualizados() {
        String statusPedidoInicial = pedidoDbResposta.getInformacoesPedido().getStatusPedido();
        String statusProduto1Inicial = pedidoDbResposta.getProdutos().get(1).getStatusProduto();
        String statusPedidoAposAttPedido = response.then().extract().as(PedidoDb.class)
                .getInformacoesPedido().getStatusPedido();
        String statusProduto1AposAttPedido = response.then().extract().as(PedidoDb.class)
                .getProdutos().get(1).getStatusProduto();

        assertThat(statusPedidoAposAttPedido).isNotEqualTo(statusPedidoInicial);
        assertThat(statusProduto1AposAttPedido).isNotEqualTo(statusProduto1Inicial);
        assertThat(statusProduto1AposAttPedido).isEqualTo(statusPedidoAposAttPedido);
    }

    @Dado("um pedido e produtos estão com status prontos")
    public void um_pedido_e_produtos_estao_com_status_prontos() {
        pedidoDbResposta = submeter_um_novo_pedido();
        String id = pedidoDbResposta.getId();
        String url = ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO + id;

        List<String> status = new ArrayList<>();
        status.add(Status.EM_PREPARACAO.getStatus());
        status.add(Status.PRONTO.getStatus());
        for (String st : status) {
            System.out.println("Atualizando para status: "+st);
            
            response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(url);
        }
    }

    @Quando("a produção tenta atualizar o status do pedido")
    public void a_producao_tenta_atualizar_o_status_do_pedido() {
        pedidoDbResposta = response.then().extract().as(PedidoDb.class);
        String id = pedidoDbResposta.getId();
        String url = ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO + id;
        // Tentando atualizar para FINALIZADO
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(url);
    }

    @Então("o status do pedido não é alterado para finalizado")
    public void o_status_do_pedudi_nao_eh_atualizado_para_finalizado() {
        assertThat(pedidoDbResposta.getInformacoesPedido().getStatusPedido())
                .isEqualTo(response.then().extract().as(PedidoDb.class)
                        .getInformacoesPedido().getStatusPedido());

        assertThat(pedidoDbResposta.getProdutos().get(0).getStatusProduto())
                .isEqualTo(response.then().extract().as(PedidoDb.class)
                        .getProdutos().get(0).getStatusProduto());
    }

    @Quando("a produção tenta atualizar o status de um produto")
    public void a_producao_tenta_atualizar_o_status_de_um_produto() {
        String id = pedidoDbResposta.getId();
        String url = ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO + id + '/' + 1;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(url);
    }

    @Então("o status do produto não é atualizado para finalizado")
    public void o_status_do_produto_nao_eh_atualizado_para_finalizado() {
        assertThat(pedidoDbResposta.getInformacoesPedido().getStatusPedido())
                .isEqualTo(response.then().extract().as(PedidoDb.class)
                        .getInformacoesPedido().getStatusPedido());

        assertThat(pedidoDbResposta.getProdutos().get(0).getStatusProduto())
                .isEqualTo(response.then().extract().as(PedidoDb.class)
                        .getProdutos().get(0).getStatusProduto());
    }

    @Quando("o cliente faz a retirada dos produtos")
    public void o_cliente_faz_a_retirada_dos_produtos() {
        Random random = new Random();

        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedidoId(random.nextLong(9999L));
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(dadosProdutosDto)
                .when().post(ENDPOINT_API_PRODUCAO_INSERIR);
        pedidoDbResposta = response.then().extract().as(PedidoDb.class);

        String id = pedidoDbResposta.getId();
        String url = ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO + id;

        List<String> status = new ArrayList<>();
        status.add(Status.EM_PREPARACAO.getStatus());
        status.add(Status.PRONTO.getStatus());
        for (String st : status) {
            System.out.println("Atualizando para status: "+st);

            response = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .when().put(url);
        }
        id = String.valueOf(pedidoDbResposta.getInformacoesPedido().getIdPedido());
        url = ENDPOINT_API_PRODUCAO_RETIRADA_PEDIDO + id;

        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put(url);


    }

    @Então("o status do pedido e produtos é atualizada para Finalizada")
    public void o_status_do_pedido_e_produtos_eh_atualizada_para_finalizada() {
        assertThat(pedidoDbResposta.getInformacoesPedido().getStatusPedido())
                .isNotEqualTo(response.then().extract().as(PedidoDb.class)
                        .getInformacoesPedido().getStatusPedido());

        assertThat(pedidoDbResposta.getProdutos().get(0).getStatusProduto())
                .isNotEqualTo(response.then().extract().as(PedidoDb.class)
                        .getProdutos().get(0).getStatusProduto());
    }



}
