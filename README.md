# API REST — Raízes do Nordeste

API Back-End desenvolvida em **Java com Spring Boot** para a rede de restaurantes **Raízes do Nordeste** (Projeto Multidisciplinar — Trilha Back-End / ADS — UNINTER).

---

## 1. Requisitos (Linguagem, Versão, Banco e Dependências)

* **Linguagem:** Java 17 ou superior (recomendado Java 21 LTS)
* **Framework:** Spring Boot 3.x
* **Gerenciador de Build/Dependências:** Apache Maven 3.9+ (ou Maven Wrapper `./mvnw` incluso no projeto)
* **Banco de Dados:** Relacional (PostgreSQL / MySQL / H2 — configurado via `application.properties`)
* **Principais Dependências (`pom.xml`):**
  * `spring-boot-starter-web` (API REST)
  * `spring-boot-starter-data-jpa` (ORM / Persistência)
  * `spring-boot-starter-validation` (Validação de DTOs com `@Valid`)
  * `spring-boot-starter-security` (Autenticação HTTP Basic, perfis `ADMIN`/`CLIENTE` e hash `BCrypt`)
  * `springdoc-openapi-starter-webmvc-ui` (Documentação Swagger / OpenAPI 3)

---

## 2. Como Configurar Variáveis de Ambiente

1. Localize o arquivo `.env.example` na raiz do repositório.
2. Crie uma cópia renomeada para `.env` (ou configure as mesmas chaves diretamente em `src/main/resources/application.properties` / variáveis de ambiente do sistema):
   ```bash
   cp .env.example .env
   
Preencha os valores de DB_URL, DB_USERNAME e DB_PASSWORD de acordo com o seu banco local.

3. Como Instalar as Dependências
Clone o repositório e execute o Maven para baixar todas as dependências do pom.xml:

'''
git clone https://github.com/Elloisy/raizes-do-nordeste-api.git
cd api
./mvnw clean install -DskipTests
'''

(No Windows PowerShell/CMD, utilize mvnw.cmd clean install -DskipTests.)

4. Como Criar o Banco de Dados e Executar Migrations / Seed
Criação do banco: Caso utilize PostgreSQL ou MySQL local, crie o banco de dados antes de iniciar a aplicação:

SQL
CREATE DATABASE raizes_nordeste;

Migrations (DDL automático): As tabelas (usuario, unidades, produtos, estoque_unidade, pedidos, itens_pedido e pagamentos_mock) são criadas e atualizadas automaticamente pelo Hibernate / Spring Data JPA (spring.jpa.hibernate.ddl-auto=update) na primeira inicialização da API.

Seed (Dados Iniciais): Os perfis de acesso padrão (admin/admin com papel ADMIN e cliente/cliente com papel CLIENTE) são carregados automaticamente em memória pelo SecurityConfig com criptografia BCrypt. Os demais dados de negócio (unidades, produtos, estoque e pedidos) podem ser criados em sequência usando a coleção do Postman.

5. Como Iniciar a API
Via terminal (Maven Wrapper):

'''
./mvnw spring-boot:run
'''
(Ou execute a classe principal com.raizes.api.ApiApplication diretamente pela ide.)

A API estará disponível em: http://localhost:8080

6. Como Acessar a Documentação (Swagger / OpenAPI)
Com a aplicação em execução, acesse:

Swagger UI (interativo): http://localhost:8080/swagger-ui/index.html

Especificação OpenAPI (JSON): http://localhost:8080/v3/api-docs

7. Como Rodar os Testes
7.1. Testes Automatizados (Maven / JUnit)
Para executar a suíte de testes unitários/contexto do Spring boot:

'''
./mvnw test
'''

7.2. Testes de API / Integração (Postman — 14 cenários)
Importe o arquivo da coleção disponível neste repositório: ./raizes-do-nordeste.postman.json.

Com a API rodando em http://localhost:8080, execute as requisições na ordem da coleção para validar:

Fluxo completo (MVP): Cadastro de Unidade, Produto, Estoque, Usuário, Pedido Multicanal (canalPedido: "TOTEM"), Pagamento mock aprovado, Atualização de Status e Filtro por Canal (GET /api/pedidos?canalPedido=TOTEM).

Cenários de Segurança e Erro JSON padronizado: 401 (sem autenticação), 403 (cliente sem permissão), 400 (validação), 404 (recurso não encontrado), 409 (estoque insuficiente) e 422 (pagamento mock recusado).

8. Evidências e Entregáveis no Repositório
Repositório GitHub: https://github.com/Elloisy/raizes-do-nordeste-api

Swagger Local: http://localhost:8080/swagger-ui/index.html

Coleção Postman (14 cenários): ./raizes-do-nordeste.postman.json

Modelo de Dados (DER): ./DER.pdf

9. Autora
Elloisy Andrade de Oliveira Almeida — RU: 4867483

Análise e Desenvolvimento de Sistemas — UNINTER (2026)