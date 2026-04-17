# 🍕 DeliveryFácil - Sistema de Gerenciamento de Delivery

## Tema: Restaurante / Delivery de Comida

## 👥 Integrantes do Grupo

| Nome Completo | RA |
|---|---|
| Victor de Souza Maciel | RGM-45450811 |
| Nathan de Oliveira Gomes | RGM-39997243 |
| Thiago Henrique dos Santos Freitas | RGM-40564100 |
| Willian Carvalho de Oliveira | RGM-34168133 |


## 🎯 Objetivo do Sistema

O **DeliveryFácil** é um sistema de gerenciamento de delivery de restaurante desenvolvido em Java. O sistema permite o controle completo do ciclo de vida de um pedido: desde o cadastro de clientes e produtos, passando pela criação e acompanhamento de pedidos, até a gestão dos entregadores responsáveis pela entrega.

O sistema foi projetado para ser simples, funcional e robusto, aplicando os princípios de Programação Orientada a Objetos como encapsulamento, validação de dados e separação de responsabilidades entre as classes.

O foco principal é oferecer uma interface de menu via terminal que permita ao operador do restaurante gerenciar todas as entidades do negócio de forma intuitiva e eficiente, sem a necessidade de sistemas externos ou banco de dados.

---

## ⚙️ Funcionalidades Principais

1. **Cadastro de Clientes** — Registrar clientes com nome, telefone e endereço de entrega
2. **Cadastro de Produtos** — Cadastrar itens do cardápio com nome, preço e categoria
3. **Cadastro de Entregadores** — Registrar entregadores com nome, veículo e status de disponibilidade
4. **Criação de Pedidos** — Montar pedidos associando cliente, itens e calcular o valor total automaticamente
5. **Atualização de Status do Pedido** — Acompanhar o pedido nos status: AGUARDANDO → EM_PREPARO → SAIU_PARA_ENTREGA → ENTREGUE
6. **Listagem Geral** — Visualizar todos os clientes, produtos, entregadores e pedidos cadastrados
7. **Busca de Pedidos por Cliente** — Consultar todos os pedidos de um cliente específico

---

## 🏗️ Estrutura de Classes Planejada

```
DeliveryFácil/
├── src/
│   ├── Main.java          → Menu principal e navegação
│   ├── Cliente.java       → Dados do cliente (nome, telefone, endereço)
│   ├── Produto.java       → Itens do cardápio (nome, preço, categoria)
│   ├── Entregador.java    → Dados do entregador (nome, veículo, disponível)
│   ├── Pedido.java        → Pedido com lista de produtos e status
│   └── ItemPedido.java    → Associação produto + quantidade dentro de um pedido
└── README.md
```

---

## 📋 Regra de Negócio Complexa

**Regra de Validação de Pedido e Cálculo Automático de Total:**

Um pedido só pode ser criado se:
- O cliente informado estiver previamente cadastrado no sistema
- O pedido tiver **pelo menos 1 item** adicionado
- Cada item deve ter quantidade maior que zero e preço maior que zero

O valor total do pedido é calculado automaticamente ao adicionar itens, somando `preço × quantidade` de cada `ItemPedido`. Qualquer tentativa de criar um pedido vazio ou com dados inválidos é bloqueada pelo sistema com mensagem de erro, garantindo a integridade dos dados.

Além disso, um pedido só pode avançar de status de forma sequencial (não é permitido pular etapas), refletindo o fluxo real de um delivery.
