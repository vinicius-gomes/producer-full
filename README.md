# producer-full

Se deseja aprender sobre Quarkus, visite o site: https://quarkus.io/ .

Este código passou por alterações que visam torna-lo independente da interação com outras APIs 
para propósitos de testes e/ou avaliação do mesmo. Logo, serviços como o RestClient implementado e seus respectivos testes
não estão habilitados na atual versão.

Este serviço se propõe a receber, validar e emitir para um tópico Kafka uma mensagem no 
formato JSON com uma estrutura especifica (aqui referida como um pedido).
Maiores detalhes sobre a implementação deste serviço podem ser encontrados na forma dos testes unitários implementados.
----------
## Executando a aplicação em dev:
Para executar o projeto de forma local corretamente, utilizamos um cluster criado usando
docker compose.

Uma vez no diretório do projeto, execute o comando:
```shell script
docker-compose up
```
Execute a aplicação:
```shell script
./mvnw compile quarkus:dev
```

Debugando a aplicação:

- Intellij: Acessar a ferramenta Attach to Process na aba Run da IDE (Ctrl+Alt+5) e selecionar o processo rodando na porta 5005 do host.

----------
## Gerando .jar e executando a aplicação
Para gerar o .jar executável, use:
```shell script
./mvnw package
```
O comando acima produz `quarkus-run.jar` no diretório `target/quarkus-app/` .

A aplicação agora pode ser executada:
```shell script
java -jar target/quarkus-app/quarkus-run.jar
```
----------
