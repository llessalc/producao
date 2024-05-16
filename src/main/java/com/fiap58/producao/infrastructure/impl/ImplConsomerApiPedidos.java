package com.fiap58.producao.infrastructure.impl;

import com.fiap58.producao.infrastructure.ConsomerApiPedidos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ImplConsomerApiPedidos implements ConsomerApiPedidos {

    private RestTemplate restTemplate ;

    @Value("${baseURLPedidos}")
    private String baseURL;

    public ImplConsomerApiPedidos() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public void roolbackPedidoFinalizado(Long id) {
        String URL = baseURL + id;
        //restTemplate.postForEntity(URL, null, String.class);
    }
}
