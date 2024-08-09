package com.fiap58.producao.infrastructure.impl;

import com.fiap58.producao.core.dto.DadosProdutosDto;
import com.fiap58.producao.core.dto.PedidoFinaliza;
import com.fiap58.producao.infrastructure.IQueueConsumer;
import com.fiap58.producao.service.ProducaoService;
import com.google.gson.Gson;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class QueueConsumer implements IQueueConsumer {

    @Autowired
    private ProducaoService service;

    @Override
    @SqsListener(value="${events.queue-pedido-producao}", pollTimeoutSeconds="20")
    public void criaPedido(String pedidoConfirmado){

        Gson gson = new Gson();
        DadosProdutosDto pedidoProducao = gson.fromJson(pedidoConfirmado, DadosProdutosDto.class);

        System.out.println(pedidoProducao);

        service.inserirPedido(pedidoProducao);

    }

    @Override
    @SqsListener(value="${events.queue-finaliza-pedido}", pollTimeoutSeconds="20")
    public void finalizaPedido(String idPedidoFinalizado){

        //Long idPedido = Long.valueOf(idPedidoFinalizado);

        Gson gson = new Gson();
        PedidoFinaliza idPedido = gson.fromJson(idPedidoFinalizado, PedidoFinaliza.class);

        System.out.println(idPedido);

        service.retiradaPedido(idPedido.idPedido());

    }
}
