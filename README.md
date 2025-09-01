# Vendacar - Revenda de Veículos Automotores

Este é um projeto backend em **Kotlin + Spring Boot** para gerenciar o cadastro, edição e venda de veículos automotores.  
O projeto é estruturado seguindo conceitos de **DDD** e **Arquitetura Hexagonal**, e está preparado para rodar localmente com **H2 (banco em memória)** para desenvolvimento rápido.

---

## 🛠 Stack Tecnológica

- **Linguagem:** Kotlin 1.9.25
- **Framework:** Spring Boot 3.5.5
- **Banco (dev):** H2 Database (em memória)
- **Build:** Gradle Kotlin DSL
- **API Docs:** Springdoc OpenAPI (Swagger UI)

## 🧩 Arquitetura Hexagonal

Este projeto segue uma arquitetura hexagonal, que separa o núcleo de negócio das tecnologias externas da seguinte forma:

- **domain** → contém as entidades e ports (interfaces) que definem contratos do domínio.

- **app** → concentra os casos de uso (regras de negócio) e a facade, que orquestra as operações.

- **infra** → implementa os adapters, como persistência em banco de dados via JPA.

- **web** → expõe a aplicação via controllers REST, além de DTOs (request/response) e mapeadores.

- **resources** → arquivos de configuração e assets estáticos.

## 📌 API Endpoints

A API expõe endpoints REST para gerenciar veículos e registrar vendas.

- **POST /vehicles** → Cadastra um novo veículo.

- **GET /vehicles/{id}** → Consulta os dados de um veículo específico.

- **GET /vehicles** → Lista veículos.

  - Parâmetros opcionais:

    - `sold` → filtra por vendidos (`true`) ou disponíveis (`false`).

    - `orderByPrice` → ordena o resultado por preço (`true`), padrão é `false`.

- **PUT /vehicles/{id}** → Atualiza os dados de um veículo.

- **POST /vehicles/{id}/sell** → Registra a venda de um veículo.

---

## 🚀 Rodando Localmente

1. Clone o repositório:
   ```bash
   git clone git@github.com:Vitor-Felix/vendacar.git
   cd vendacar

2. Build e run:
   ```bash
   ./gradlew bootRun

3. Acesse a aplicação:

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:vendacar

## 🚀 Rodando com Docker

1. Build e suba os contêineres com:
   ```bash
   docker compose up --build
   
2. O Swagger (documentação da API) pode ser acessado em:
   ```bash
   http://localhost:8080/swagger-ui.html

3. Para parar a aplicação:
   ```bash
   docker compose down
   