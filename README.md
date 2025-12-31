# Vendacar - Revenda de Veículos Automotores

Este é um projeto backend em **Kotlin + Spring Boot** para gerenciar o cadastro, edição e venda de veículos automotores.  
O projeto segue princípios de **DDD** e **Arquitetura Hexagonal**, garantindo separação clara entre domínio e infraestrutura.

A aplicação conta com autenticação via **Keycloak**, incluindo registro e login de usuários com validação de CPF.

## 🛠 Stack Tecnológica

- **Linguagem:** Kotlin 1.9.25
- **Framework:** Spring Boot 3.5.5
- **Banco Dev:** H2 Database (em memória)
- **Banco Prod / Docker:** PostgreSQL
- **Autenticação & IAM:** Keycloak 24
- **Build:** Gradle Kotlin DSL
- **API Docs:** Springdoc OpenAPI (Swagger UI)
- **Segurança:** OAuth2 Resource Server + JWT

---

## 🔐 Autenticação com Keycloak

A API utiliza o **Keycloak** para autenticação e autorização via **JWT Bearer Token**.

Funcionalidades implementadas:

| Funcionalidade | Status |
|---|:---:|
| Login e senha via Keycloak | ✅ |
| Registro de usuário via API | ✅ |
| Validação de atributos personalizados (CPF) | ✅ |
| Acesso autenticado para comprar veículos | ✅ |

### 📍 Endpoints de autenticação

| Método | URL | Descrição | Autenticação |
|---|---|---|:---:|
| POST | `/auth/register` | Cria usuário no Keycloak (atributo CPF incluso) | ❌ |
| POST | `/auth/login` | Retorna JWT para chamadas protegidas | ❌ |

Payload do registro 👇
```json
{
  "username": "buyer1",
  "email": "buyer@mail.com",
  "password": "123456",
  "cpf": "11122233344"
}
```

### 🔑 Fluxo para endpoints protegidos

1. Registrar usuário
2. Fazer login com:
```
POST /auth/login
```
3. Copiar access_token
4. Enviar como Authorization:
```
Authorization: Bearer <access_token>
```

---
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
- Keycloak (possui Docker separado): http://localhost:8081

## 🚀 Rodando com Docker

### Docker da aplicação

1. Build e suba os contêineres com:
   ```bash
   docker compose up --build
   
2. O Swagger (documentação da API) pode ser acessado em:
   ```bash
   http://localhost:8080/swagger-ui.html

3. Para parar a aplicação:
   ```bash
   docker compose down

### Docker do Keycloak

Este projeto possui autenticação configurada em::
   ```bash
   docker-compose-keycloak.yml
   ```

Subir Keycloak:
```bash
   docker compose -f docker-compose-keycloak.yml up -d
```

Para parar a aplicação:
   ```bash
   docker compose down
```

#### Credenciais administrativas:

- Usuário/Senha:
  - admin/admin
- Realm configurado: vendacar
- Client API: vendacar-api

#### 🗂 Configuração do Keycloak para o Projeto
- Recurso/Valor:
  - Realm	vendacar
  - Client	vendacar-api
  - Roles aplicadas no Service Account	manage-users, view-users
  - Custom Attribute	cpf (obrigatório)
  - Token Claim	cpf incluído no JWT

#### 📌 Uso do atributo cpf
→ obrigatório no registro

→ recuperado do token no endpoint /vehicles/{id}/buy

---
## ☸️ Rodando com Kubernetes (Minikube)
Para executar o projeto no Kubernetes, usamos o Minikube para simular um cluster local.
Os manifests estão localizados na pasta deploy/ e incluem os arquivos:
- configmap.yaml → define variáveis de ambiente não sensíveis
- secret.yaml → armazena credenciais e dados sensíveis (como senhas)
- deploy-postgres.yaml → configura o banco de dados PostgreSQL
- deploy-vendacar.yaml → define o deployment e service da aplicação

1. Inicie o Minikube:
   ```bash
   minikube start

2. Crie um namespace para o projeto:
   ```bash
   kubectl create namespace vendacar

3. Construa e carregue a imagem Docker dentro do Minikube:
   O Minikube usa um Docker interno, separado do da sua máquina.
   Por isso, é necessário carregar a imagem para dentro dele.
   ```bash
    eval $(minikube -p minikube docker-env)
    docker build . -t vendacar

4. Aplique os manifestos:
    ```bash
    kubectl apply -f deploy/ -n vendacar
   
5. Verifique se os pods estão rodando:
    ```bash
    kubectl get pods -n vendacar

6. Expose o service do vendacar via Minikube:
    ```bash
    minikube service vendacar-service -n vendacar
