# producao
Api Producao Fiap58

## Criando container para o MongoDb
```sh
# Baixando imagem 
docker pull mongodb/mongodb-community-server

docker run --name mongo -p 27017:27017 -e MONGODB_PASS="root" -d mongodb/mongodb-community-server:latest

docker exec -it mongo mongosh


````

- Criando dados no MongoDb

``` javaScript
# Acessando database
use producao

# Criando a collection
db.createCollection("pedidos")

db.pedidos.insertOne(
{
        "id": ObjectId('6618a62aef815eb5722367cd'),
        "produtos":[
            {
                "nome":"Hamburguer",
                "observacao":"Sem picles",
                "quantidade":2,
                "status":"Recebido"
            },
            {
                "nome":"Batata Frita",
                "quantidade":1,
                "status":"Recebido"
            }
        ],
        "informacoesPedido":{
            "statusPedido":"Recebido"
        }
}
)

```