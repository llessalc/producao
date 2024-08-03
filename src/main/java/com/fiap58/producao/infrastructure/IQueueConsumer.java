package com.fiap58.producao.infrastructure;

public interface IQueueConsumer {

    void criaPedido(String pedidoConfirmado);

    void finalizaPedido(String idPedidoFinalizado);
}
