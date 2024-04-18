package com.fiap58.producao.core.usecases.bdd;

import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.usecases.utils.PedidoHelper;
import com.fiap58.producao.gateway.PedidoDb;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;
    private PedidoDb pedidoDbResposta;
    private final String ENDPOINT_API_PRODUCAO_INSERIR = "http://localhost:8080/pedidoProducao/adicionaPedido";
    private final String ENDPOINT_API_PRODUCAO_LISTAR = "http://localhost:8080/pedidoProducao/adicionaPedido";

    //Receber um pedido e listar
    @Quando("submeter um novo pedido")
    public PedidoDb submeter_um_novo_pedido() {
        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedido();
        response = given()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(dadosProdutosDto)
                    .when().post(ENDPOINT_API_PRODUCAO_INSERIR);
        pedidoDbResposta = response.then().extract().as(PedidoDb.class);
        return pedidoDbResposta;
    }

    @Então("o pedido é incluido com sucesso")
    public void pedido_incluido_com_sucesso() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
                //.body(matchesJsonSchemaInClasspath("./schemas/PedidoDb.json"));

        // Write code here that turns the phrase above into concrete actions
    }
    @Dado("que temos um pedido com mais de um produto no mesmo estado")
    public void que_temos_um_pedido_com_mais_de_um_produto_no_mesmo_estado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Quando("atualizar o status de apenas um produto")
    public void atualizar_o_status_de_apenas_um_produto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Então("o status do pedido não é alterado")
    public void o_status_do_pedido_nao_eh_alterado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Dado("que temos um pedido com mais de um produto em status diferentes")
    public void que_temos_um_pedido_com_mais_de_um_produto_em_status_diferentes() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("atualizar um produto com status inferior aos demais")
    public void atualizar_um_produto_com_status_inferior_aos_demais() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Então("o status do pedido é atualizado para o mesmo status do produto atualizado")
    public void o_status_do_pedido_eh_atualizado_para_o_mesmo_status_do_produto_atualizado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("atualizar o status do pedido")
    public void atualizar_o_status_do_pedido() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Então("o status dos produtos com status inferior também são atualizados")
    public void o_status_dos_produtos_com_status_inferior_tambem_sao_atualizados() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Dado("um pedido e produtos estão com status prontos")
    public void um_pedido_e_produtos_estao_com_status_prontos() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("a produção tenta atualizar o status do pedido")
    public void a_producao_tenta_atualizar_o_status_do_pedido() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("a produção tenta atualizar o status de um produto")
    public void a_producao_tenta_atualizar_o_status_de_um_produto() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Então("o status do produto não é atualizado")
    public void o_status_do_produto_nao_eh_atualizado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("o cliente faz a retirada dos produtos")
    public void o_cliente_faz_a_retirada_dos_produtos() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Então("o status do pedido e produtos é atualizada para Finalizada")
    public void o_status_do_pedido_e_produtos_eh_atualizada_para_finalizada() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Quando("listamos os pedidos")
    public void listamos_os_pedidos() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Então("o pedido finalizado não é retornado")
    public void o_pedido_finalizado_nao_eh_retornado() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
