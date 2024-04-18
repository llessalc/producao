# language: pt
Funcionalidade: API - Producao

  Cenário: Receber um pedido e listar
    Quando submeter um novo pedido
    Então o pedido é incluido com sucesso

  Cenário: Atualizar o status de um produto
    Dado que temos um pedido com mais de um produto no mesmo estado
    Quando atualizar o status de apenas um produto
    Então o status do pedido não é alterado

  Cenário: Atualizar o status de um produto tendo outros pedidos com status superior
    Dado que temos um pedido com mais de um produto em status diferentes
    Quando atualizar um produto com status inferior aos demais
    Então o status do pedido é atualizado para o mesmo status do produto atualizado

  Cenário: Atualizar o status de um pedido
    Quando atualizar o status do pedido
    Então o status dos produtos com status inferior também são atualizados

  Cenário: Tentar atualizar status de pedido e produtos prontos
    Dado um pedido e produtos estão com status prontos
    Quando a produção tenta atualizar o status do pedido
    Então o status do pedido não é alterado para finalizado
    Quando a produção tenta atualizar o status de um produto
    Então o status do produto não é atualizado para finalizado

  Cenário: Cliente retira produto em loja
    Quando o cliente faz a retirada dos produtos
    Então o status do pedido e produtos é atualizada para Finalizada