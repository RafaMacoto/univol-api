# Univol - Sistema Inteligente de Classificação de Pedidos

## Descrição do Projeto
Este projeto propõe uma solução inteligente para a gestão de pedidos de organizações voluntárias, automatizando a classificação de prioridade dos pedidos com base no conteúdo da descrição. Utiliza-se uma API de Machine Learning externa para realizar a avaliação, salvando tanto a prioridade no próprio pedido quanto o histórico dessa classificação em uma tabela de log.


## Solução
- Ao cadastrar um novo pedido, sua descrição é enviada automaticamente a uma API de Machine Learning.
- O modelo avalia se a prioridade do pedido é Alta, Média ou Baixa, retornando a resposta em tempo real.
- Essa prioridade é então gravada diretamente no campo `prioridade` do pedido.
- Um log completo da avaliação (prioridade, modelo utilizado, data) é armazenado na entidade `LogPrioridade`.

## Integrantes do Grupo
- Rafael Macoto
- Gabrielly Macedo
- Fernando Aguiar

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Maven
- Spring Web
- Spring Security
- Autenticação JWT
- JPA / Hibernate
- Lombok
- REST APIs
- Docker (opcional)
- Oracle

## Estrutura do Projeto
- `src/model/` → Entidades JPA (Pedido, LogPrioridade, etc)
- `src/service/` → Contém as regras de negócio e integração com a API externa
- `src/controller/` → Camada REST para comunicação externa
- `src/repository/` → Interfaces JPA para persistência
- `src/dto/` → Objetos de transferência de dados
- `src/mapper/` → Conversões entre entidades e DTOs
- `src/specification/` → Filtros dinâmicos para busca de pedidos
- `pom.xml` → Gerenciamento de dependências
- `Dockerfile` → Para construção de imagem Docker

## Pré-requisitos
- Java JDK 21 ou superior
- Maven 3.6+
- Git
- Docker (opcional)

## Como Executar

### Executando Localmente com Maven
```bash

git clone https://github.com/RafaMacoto/univol-backend.git
cd univol-backend
./mvnw spring-boot:run
Acesse a aplicação em:
http://localhost:8080

Executando com Docker

docker build -t univol-backend .
docker run -p 8080:8080 univol-backend
Integração com API de Machine Learning
URL: https://conectavoluntario-ml-api.onrender.com/classificar

```


## Funcionalidades

Cadastro automático de pedidos com classificação de prioridade via API de IA

Armazenamento do resultado da classificação

Consulta de logs de classificação

Filtros dinâmicos para buscar pedidos por parâmetros variados

Associação de pedidos a organizações e usuários

Preparação para autenticação e controle de acesso
