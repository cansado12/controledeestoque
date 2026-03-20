# 📦 Controle de Estoque API

API RESTful para gerenciamento de produtos e fornecedores, desenvolvida com **Spring Boot 3.4** e **Java 17**.

---

## 🧪 Tecnologias Utilizadas

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| Spring Boot | 3.4.0 |
| Spring Data JPA | - |
| Spring Validation | - |
| MySQL | 8 |
| MapStruct | 1.5.5.Final |
| SpringDoc OpenAPI (Swagger) | 2.8.5 |
| Docker / Docker Compose | - |
| Maven | 3.9 |

---

## 🏗️ Arquitetura do Projeto

```
src/
└── main/
    └── java/com/controle/estoque/controledeestoque/
        ├── annotation/swagger/     # Anotações customizadas do Swagger
        ├── config/                 # Configurações (WebConfig - content negotiation)
        ├── controller/             # Camada de apresentação (REST)
        ├── DTO/                    # Records de entrada e saída
        ├── exception/              # Exceções customizadas + GlobalExceptionHandler
        ├── mapper/custom/          # Mappers MapStruct (Produto e Fornecedor)
        ├── model/                  # Entidades JPA (Produto, Fornecedor, Categoria)
        ├── repository/             # Interfaces Spring Data JPA
        └── service/                # Regras de negócio
```

---

## 🗃️ Modelo de Dados

### Entidades

**Produto**
- `id` — identificador único
- `nome` — nome do produto
- `preco` — preço (`BigDecimal`)
- `quantidade` — estoque disponível
- `categoria` — enum `Categoria`
- `fornecedores` — lista de fornecedores (ManyToMany)

**Fornecedor**
- `id` — identificador único
- `nome` — nome do fornecedor
- `cnpj` — CNPJ
- `email` — endereço de e-mail
- `telefone` — telefone de contato
- `produtos` — lista de produtos (ManyToMany, lado inverso)

### Relacionamento

`Produto` e `Fornecedor` possuem um relacionamento **Many-to-Many**, gerenciado pela tabela intermediária `produto_fornecedor`.

### Categorias disponíveis

`ELETRONICOS`, `ALIMENTOS`, `VESTUARIO`, `HIGIENE_PESSOAL`, `LIMPEZA`, `INFORMATICA`, `MOVEIS`, `FERRAMENTAS`, `PAPELARIA`, `OUTROS`

---

## 🔌 Endpoints

### Produtos — `/produto`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/produto` | Lista todos os produtos (paginado) |
| `GET` | `/produto/{id}` | Busca produto por ID |
| `GET` | `/produto/categoria/{categoria}` | Filtra por categoria (paginado) |
| `POST` | `/produto` | Cria um novo produto |
| `PUT` | `/produto/{id}` | Atualiza um produto |
| `DELETE` | `/produto/{id}` | Remove um produto |

**Parâmetros de paginação:** `page` (padrão: 0), `size` (padrão: 10), `sort` (padrão: `id`)

**Formatos aceitos:** `application/json`, `application/xml`, `application/yaml`

---

### Fornecedores — `/fornecedor`

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/fornecedor` | Lista todos os fornecedores (paginado) |
| `GET` | `/fornecedor/{id}` | Busca fornecedor por ID |
| `POST` | `/fornecedor` | Cria um novo fornecedor |
| `PUT` | `/fornecedor/{id}` | Atualiza um fornecedor |
| `DELETE` | `/fornecedor/{id}` | Remove um fornecedor |
| `GET` | `/fornecedor/{fornecedorId}/produto` | Lista produtos de um fornecedor |
| `PUT` | `/fornecedor/{fornecedor}/{produtoId}` | Vincula produto a um fornecedor |
| `DELETE` | `/fornecedor/{fornecedor}/{produtoId}` | Remove vínculo produto-fornecedor |

**Parâmetros de paginação:** `page` (padrão: 0), `size` (padrão: 10), `ordem` (padrão: `id`)

---

## ⚠️ Tratamento de Erros

Todos os erros são tratados pelo `GlobalExceptionHandler` e retornam o seguinte formato:

```json
{
  "status": 404,
  "erro": "Não Encontrado",
  "mensagem": "Produto com id 5 não encontrado"
}
```

| Exceção | HTTP Status |
|---|---|
| `ProdutoNotFoundException` | 404 |
| `FornecedorNotFoundException` | 404 |
| `EstoqueInsuficienteException` | 400 |
| `ProdutoInvalidoException` | 400 |
| `MethodArgumentNotValidException` | 400 |
| Exceções genéricas | 500 |

---

## 🚀 Como Executar

### Pré-requisitos

- Docker e Docker Compose instalados

### Subindo o ambiente completo

```bash
docker-compose up --build
```

A API estará disponível em: `http://localhost:8080`

### Resetando o ambiente (limpa volumes e imagens)

```bash
docker-compose down -v --rmi local && docker-compose up --build
```

### Executando localmente (sem Docker)

Certifique-se de ter um MySQL rodando na porta `3306` com o banco `produtodb` e as credenciais `root/root`, então:

```bash
./mvnw spring-boot:run
```

---

## 📄 Documentação Swagger

Com a aplicação rodando, acesse:

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api-docs`

---

## ✅ Testes

O projeto contém testes unitários para as camadas de serviço utilizando **JUnit 5** e **Mockito**.

```bash
./mvnw test
```

---

## 📁 Variáveis de Ambiente (Docker)

| Variável | Valor padrão |
|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://mysql:3306/produtodb?useTimezone=true&serverTimezone=UTC` |
| `SPRING_DATASOURCE_USERNAME` | `root` |
| `SPRING_DATASOURCE_PASSWORD` | `root` |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` |
