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
- **Testes:** JUnit 5 + Kotlin Test

---

## 🚀 Rodando Localmente

1. Clone o repositório:
   ```bash
   git clone git@github.com:Vitor-Felix/vendacar.git
   cd vendacar

2. Build e run:
   ```bash
   ./gradlew bootRun

1. Acesse a aplicação:

- API: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:vendacar
