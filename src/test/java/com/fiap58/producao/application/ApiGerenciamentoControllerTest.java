package com.fiap58.producao.application;

import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.domain.Status;
import com.fiap58.producao.core.usecases.AtualizarStatusPedido;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import com.fiap58.producao.infrastructure.impl.PedidoDbImpl;
import com.fiap58.producao.service.ProducaoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiGerenciamentoControllerTest {

    private MockMvc mockMvc;
    private AutoCloseable openMocks;
    @Mock
    private ProducaoService service;
    @Mock
    private PedidoDbImpl pedidoDbImpl;

    private List<Produto> produtos = new ArrayList<>();
    private InformacoesPedido informacoesPedido;
    private PedidoDb pedidoDb;

    private final String ENDPOINT_API_PRODUCAO_RETIRADA_PEDIDO = "http://localhost:8080/pedidoProducao/retiradaPedido/{idPedido}";

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
        ApiGerenciamentoController apiGerenciamentoController = new ApiGerenciamentoController(pedidoDbImpl, service);

        Produto prod1 = new Produto();
        prod1.setNome("X-Burguer Test");
        prod1.setObservacao("Sem picles");
        prod1.setQuantidade(2);
        prod1.setStatusProduto(Status.FINALIZADO.getStatus());
        Produto prod2 = new Produto();
        prod2.setNome("Batata Frita");
        prod2.setQuantidade(1);
        prod2.setStatusProduto(Status.FINALIZADO.getStatus());

        produtos.add(prod1);
        produtos.add(prod2);

        informacoesPedido = new InformacoesPedido();
        informacoesPedido.setStatusPedido(Status.FINALIZADO.getStatus());


        pedidoDb = new PedidoDb();
        pedidoDb.setId("1");
        pedidoDb.setProdutos(produtos);
        pedidoDb.setInformacoesPedido(informacoesPedido);

        mockMvc = MockMvcBuilders.standaloneSetup(apiGerenciamentoController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void retiradaPedido() throws Exception {
        Long idPedido = 1L;
        when(service.retiradaPedido(anyLong())).thenReturn(pedidoDb);

        mockMvc.perform(put(ENDPOINT_API_PRODUCAO_RETIRADA_PEDIDO, idPedido)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).retiradaPedido(anyLong());
    }
}