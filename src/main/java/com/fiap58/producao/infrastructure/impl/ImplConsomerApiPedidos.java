package com.fiap58.producao.infrastructure.impl;

import com.fiap58.producao.infrastructure.ConsomerApiPedidos;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ImplConsomerApiPedidos implements ConsomerApiPedidos {

    private RestTemplate restTemplate ;

    private String baseURL;

    public ImplConsomerApiPedidos(@Value("${baseURLPedidos}") String baseURL) {
        this.baseURL = baseURL;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void roolbackPedidoFinalizado(Long id) {
        String URL = baseURL + id;
        System.out.println(URL);
        restTemplate.postForEntity(URL, null, String.class);
    }
}
