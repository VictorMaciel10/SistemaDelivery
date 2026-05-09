-- ============================================================
--  Script SQL - Sistema DeliveryFácil CP3
--  Banco de dados: PostgreSQL
--  Execute este script antes de rodar o sistema Java
-- ============================================================

-- Criar o banco de dados (execute separadamente se necessário)
-- CREATE DATABASE delivery_db;

-- ─────────────────────────────────────────────
-- Tabela de Clientes
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS clientes (
    id       SERIAL PRIMARY KEY,
    nome     VARCHAR(100) NOT NULL,
    telefone VARCHAR(20)  NOT NULL,
    endereco VARCHAR(200) NOT NULL
);

-- ─────────────────────────────────────────────
-- Tabela de Produtos
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS produtos (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100)   NOT NULL,
    preco     NUMERIC(10, 2) NOT NULL CHECK (preco > 0),
    categoria VARCHAR(50)    NOT NULL
);

-- ─────────────────────────────────────────────
-- Tabela de Entregadores
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS entregadores (
    id         SERIAL PRIMARY KEY,
    nome       VARCHAR(100) NOT NULL,
    veiculo    VARCHAR(50)  NOT NULL,
    disponivel BOOLEAN      NOT NULL DEFAULT TRUE
);

-- ─────────────────────────────────────────────
-- Tabela de Pedidos
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS pedidos (
    id         SERIAL PRIMARY KEY,
    cliente_id INTEGER     NOT NULL REFERENCES clientes(id),
    status     VARCHAR(30) NOT NULL DEFAULT 'AGUARDANDO'
);

-- ─────────────────────────────────────────────
-- Tabela de Itens do Pedido
-- ─────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS itens_pedido (
    id         SERIAL PRIMARY KEY,
    pedido_id  INTEGER        NOT NULL REFERENCES pedidos(id),
    produto_id INTEGER        NOT NULL REFERENCES produtos(id),
    quantidade INTEGER        NOT NULL CHECK (quantidade > 0),
    subtotal   NUMERIC(10, 2) NOT NULL
);

-- ─────────────────────────────────────────────
-- Dados de exemplo para teste (opcional)
-- ─────────────────────────────────────────────
INSERT INTO clientes (nome, telefone, endereco) VALUES
    ('Maria Silva',  '11999990001', 'Rua das Flores, 10'),
    ('Pedro Costa',  '11999990002', 'Av. Paulista, 500'),
    ('Ana Souza',    '11999990003', 'Rua Augusta, 200');

INSERT INTO produtos (nome, preco, categoria) VALUES
    ('X-Burguer',      18.90, 'Lanche'),
    ('Coca-Cola 350ml', 6.00, 'Bebida'),
    ('Batata Frita',   12.50, 'Acompanhamento'),
    ('Sundae',          8.00, 'Sobremesa');

INSERT INTO entregadores (nome, veiculo, disponivel) VALUES
    ('João Moto',    'Moto',      TRUE),
    ('Lucas Bike',   'Bicicleta', TRUE),
    ('Carlos Carro', 'Carro',     FALSE);
