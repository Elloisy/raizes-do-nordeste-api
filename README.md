# Raízes do Nordeste — API REST

API Desenvolvida para gestão completa da empresa Raízes do Nordeste
 
1. API REST desenvolvida em Java com Spring Boot para gestão de unidades, cardápio, controle de estoque por unidade, pedidos, pagamento simulado (mock) e programa de pontos de fidelidade.

2. Tecnologias utilizadas:
-Java 17/21
-Spring Boot (Web, Data JPA, Security, Validation)
-MySQL 8
-Hibernate ORM
-Lombok
-Maven

3. Pré-requisitos:
-JDK 17 ou superior
-MySQL Server rodando na porta padrão (3306)
-Postman para testes dos endpoints

4. Como executar:
-Clone o repositório:
```bash
git clone https://github.com/Elloisy/raizes-do-nordeste-api.git
```
   
   
4.1 Configure o acesso ao seu MySQL no arquivo

src/main/resources/application.properties:

```spring.datasource.url=jdbc:mysql://localhost:3306/raizes_nordeste?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=SUA_SENHA```

(O banco de dados e tabelas serão criados automaticamente pelo hibernate na primeira execução)

4.2 Execute a classe principal ApiApplication.java pela IDE ou via Maven:
```bash
./mvnw spring-boot:run
```
4.3 A API estará disponível em http://localhost:8080

5. Autenticação:
A API utiliza HTTP Basic Auth (stateless):
- Usuário: admin
- Senha: admin

6. Endpoints Principais:

- Unidades e Estoque:

-- GET /api/unidades — Lista todas as unidades cadastradas

-- POST /api/unidades — Cadastra uma nova unidade

-- GET /api/unidades/{unidadeId}/estoque — Consulta o estoque de uma unidade

-- POST /api/unidades/estoque — Cadastra o estoque de um produto na unidade

- Produtos:
-- GET /api/produtos — Lista todos os produtos ativos

-- POST /api/produtos — Cadastra um novo produto

- Usuários:

--GET /api/usuarios — Lista todos os usuários

-- POST /api/usuarios — Cadastra um novo usuário

- Pedidos e Pagamento Mock:

-- POST /api/pedidos — Cria um pedido (valida o estoque na unidade e calcula o valor total)

-- GET /api/pedidos/{id} — Consulta um pedido por ID

-- POST /api/pedidos/{id}/pagamento — Processa o pagamento simulado, altera o status do pedido para EM_PREPARO e credita +10 pontos de fidelidade ao cliente

-- PATCH /api/pedidos/{id}/status — Atualiza o status do pedido (RECEBIDO, EM_PREPARO, PRONTO, ENTREGUE, CANCELADO)
