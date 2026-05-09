# DeliveryFácil — CP3

## Estrutura de Pacotes

```
src/
├── Main.java               ← Classe principal (fora dos pacotes, ponto de entrada)
├── model/
│   ├── Pessoa.java         ← Classe ABSTRATA (CP3)
│   ├── Cliente.java
│   ├── Entregador.java
│   ├── Administrador.java
│   ├── Produto.java
│   ├── ItemPedido.java
│   └── Pedido.java
├── dao/
│   ├── ClienteDAO.java     ← CRUD completo via JDBC
│   ├── ProdutoDAO.java     ← CRUD completo via JDBC
│   └── EntregadorDAO.java  ← CRUD completo via JDBC
└── util/
    └── ConexaoBD.java      ← Gerencia conexão PostgreSQL

sql/
└── criar_tabelas.sql       ← Script para criar as tabelas
```

## Configuração do Banco de Dados

### 1. Criar o banco no PostgreSQL
```sql
CREATE DATABASE delivery_db;
```

### 2. Executar o script SQL
```bash
psql -U postgres -d delivery_db -f sql/criar_tabelas.sql
```

### 3. Ajustar credenciais (se necessário)
Edite o arquivo `src/util/ConexaoBD.java` e altere:
```java
private static final String URL     = "jdbc:postgresql://localhost:5432/delivery_db";
private static final String USUARIO = "postgres";
private static final String SENHA   = "postgres";
```

## Dependência: Driver JDBC do PostgreSQL

Baixe o driver em: https://jdbc.postgresql.org/download/

Coloque o arquivo `.jar` na pasta `lib/` e inclua no classpath ao compilar.

## Como Compilar e Executar

```bash
# Compilar (com o driver JDBC no classpath)
javac -cp "lib/postgresql-42.x.x.jar" -d out src/util/*.java src/model/*.java src/dao/*.java src/Main.java

# Executar
java -cp "out:lib/postgresql-42.x.x.jar" Main
```

> No Windows, use `;` em vez de `:` no classpath.

## Novidades do CP3

- `Pessoa` agora é **classe abstrata** com método abstrato `exibirPermissoes()`
- Cada subclasse (`Cliente`, `Entregador`, `Administrador`) implementa `exibirPermissoes()`
- CRUD completo via **padrão DAO** para Clientes, Produtos e Entregadores
- Conexão com **PostgreSQL via JDBC** usando `PreparedStatement`
- Código organizado em pacotes: `model`, `dao`, `util`
