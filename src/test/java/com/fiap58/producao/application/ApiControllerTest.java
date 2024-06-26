package com.fiap58.producao.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap58.producao.core.domain.InformacoesPedido;
import com.fiap58.producao.core.domain.Produto;
import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.service.ProducaoService;
import com.fiap58.producao.core.usecases.AtualizarStatusPedido;
import com.fiap58.producao.core.utils.PedidoHelper;
import com.fiap58.producao.infrastructure.domain.PedidoDb;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ApiControllerTest {

    private MockMvc mockMvc;
    private AutoCloseable openMocks;
    @Mock
    private ProducaoService service;

    private List<PedidoDb> pedidoDbList = new ArrayList<>();
    private AtualizarStatusPedido atualizarStatusPedido;
    private List<Produto> produtos = new ArrayList<>();
    private InformacoesPedido informacoesPedido;
    private PedidoDb pedidoDb;

    private final String ENDPOINT_API_PRODUCAO_RETORNAR = "http://localhost:8080/pedidoProducao";
    private final String ENDPOINT_API_PRODUCAO_INSERIR = "http://localhost:8080/pedidoProducao/adicionaPedido";
    private final String ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO = "http://localhost:8080/pedidoProducao/atualizarProdutoPedido/{idPedido}/{idProduto}";
    private final String ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO = "http://localhost:8080/pedidoProducao/atualizarPedido/{idPedido}";

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        ApiController apiController = new ApiController(service);
//        ApiController apiController = new ApiController(repository, implConsomerApiPedidos);
//        service = new ProducaoService(pedidoDbImpl, implConsomerApiPedidos);

        atualizarStatusPedido = new AtualizarStatusPedido();
        Produto prod1 = new Produto();
        prod1.setNome("X-Burguer Test");
        prod1.setObservacao("Sem picles");
        prod1.setQuantidade(2);
        Produto prod2 = new Produto();
        prod2.setNome("Batata Frita");
        prod2.setQuantidade(1);

        produtos.add(prod1);
        produtos.add(prod2);

        informacoesPedido = new InformacoesPedido();


        pedidoDb = new PedidoDb();
        pedidoDb.setId("1");
        pedidoDb.setProdutos(produtos);
        pedidoDb.setInformacoesPedido(informacoesPedido);

        pedidoDbList.add(pedidoDb);


        mockMvc = MockMvcBuilders.standaloneSetup(apiController)
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
    void retornaPedidosProducao() throws Exception {
        List<PedidoDb> pedidoDbList = new ArrayList<>();
        when(service.retornaPedidosProducao()).thenReturn(pedidoDbList);

        mockMvc.perform(get(ENDPOINT_API_PRODUCAO_RETORNAR)
                .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1))
                .retornaPedidosProducao();
    }

    @Test
    void inserirPedido() throws Exception {
        DadosProdutosDto dadosProdutosDto = PedidoHelper.gerarPedido();
        when(service.inserirPedido(any(DadosProdutosDto.class))).thenReturn(pedidoDb);

        mockMvc.perform(post(ENDPOINT_API_PRODUCAO_INSERIR)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dadosProdutosDto)))
                .andDo(print())
                .andExpect(status().isCreated());
        verify(service, times(1)).inserirPedido(any(DadosProdutosDto.class));


    }

    @Test
    void alteraStatusPedido() throws Exception {
        String idPedido = "qualquerId";
        when(service.atualizarStatusPedido(any(String.class))).thenReturn(pedidoDb);

        mockMvc.perform(put(ENDPOINT_API_PRODUCAO_ATUALIZAR_PEDIDO, idPedido)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).atualizarStatusPedido(any(String.class));
    }

    @Test
    void alteraStatusProduto() throws Exception {
        String idPedido = "qualquerIdPedido";
        int idProduto = 1;
        when(service.atualizarStatusProduto(any(String.class), anyInt())).thenReturn(pedidoDb);

        mockMvc.perform(put(ENDPOINT_API_PRODUCAO_ATUALIZAR_PRODUTO, idPedido, idProduto)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        verify(service, times(1)).atualizarStatusProduto(any(String.class), anyInt());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}