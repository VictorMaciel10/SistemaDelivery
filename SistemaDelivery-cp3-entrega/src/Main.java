import dao.ClienteDAO;
import dao.EntregadorDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principal do sistema DeliveryFácil.
 * CP3: Menu integrado com banco de dados via DAOs para todas as entidades.
 */
public class Main {

    // DAOs para acesso ao banco de dados
    static ClienteDAO   clienteDAO   = new ClienteDAO();
    static ProdutoDAO   produtoDAO   = new ProdutoDAO();
    static EntregadorDAO entregadorDAO = new EntregadorDAO();
    static PedidoDAO    pedidoDAO    = new PedidoDAO();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   🍕 BEM-VINDO AO DELIVERYFÁCIL v3.0 🍕  ");
        System.out.println("============================================");

        // ─────────────────────────────────────────────
        // DEMONSTRAÇÃO DE POLIMORFISMO (CP2 mantido)
        // ─────────────────────────────────────────────
        System.out.println("\n===== DEMONSTRAÇÃO DE POLIMORFISMO =====");

        ArrayList<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Cliente(1, "Maria Silva", "11999990000", "Rua das Flores, 10"));
        pessoas.add(new Entregador(2, "João Moto", "Moto", true));
        pessoas.add(new Administrador(3, "Carlos Admin", "Gerente"));

        // Polimorfismo: cada objeto chama sua versão de exibirInfo() e exibirPermissoes()
        for (Pessoa p : pessoas) {
            p.exibirInfo();
            p.exibirPermissoes(); // CP3: método abstrato implementado por cada subclasse
            System.out.println("---");
        }
        System.out.println("========================================\n");

        // ─────────────────────────────────────────────
        // MENU PRINCIPAL
        // ─────────────────────────────────────────────
        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            opcao = lerInt("Escolha uma opção: ");

            switch (opcao) {
                case 1 -> menuClientes();
                case 2 -> menuProdutos();
                case 3 -> menuEntregadores();
                case 4 -> menuPedidos();
                case 0 -> System.out.println("\n👋 Encerrando o sistema. Até logo!");
                default -> System.out.println("❌ Opção inválida! Tente novamente.\n");
            }
        }
        scanner.close();
    }

    // ─────────────────────────────────────────────
    //  MENU PRINCIPAL
    // ─────────────────────────────────────────────

    static void exibirMenuPrincipal() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("  1. 👤 Gerenciar Clientes");
        System.out.println("  2. 🍔 Gerenciar Produtos");
        System.out.println("  3. 🛵 Gerenciar Entregadores");
        System.out.println("  4. 📦 Gerenciar Pedidos");
        System.out.println("  0. 🚪 Sair");
        System.out.println("=====================================");
    }

    // ─────────────────────────────────────────────
    //  MENU CLIENTES (CRUD completo)
    // ─────────────────────────────────────────────

    static void menuClientes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
                case 3 -> atualizarCliente();
                case 4 -> excluirCliente();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }

    static void cadastrarCliente() {
        System.out.println("\n[ NOVO CLIENTE ]");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        try {
            Cliente c = new Cliente(0, nome, telefone, endereco);
            clienteDAO.inserir(c);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void listarClientes() {
        System.out.println("\n[ LISTA DE CLIENTES ]");
        List<Cliente> lista = clienteDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : lista) System.out.println(c);
    }

    static void atualizarCliente() {
        listarClientes();
        int id = lerInt("ID do cliente a atualizar: ");
        Cliente encontrado = clienteDAO.buscarPorId(id);
        if (encontrado == null) {
            System.out.println("❌ Cliente não encontrado.");
            return;
        }

        System.out.println("\n[ ATUALIZAR CLIENTE ID: " + id + " ] (Enter para manter o valor atual)");
        System.out.print("Novo nome (atual: " + encontrado.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo telefone (atual: " + encontrado.getTelefone() + "): ");
        String telefone = scanner.nextLine();
        System.out.print("Novo endereço (atual: " + encontrado.getEndereco() + "): ");
        String endereco = scanner.nextLine();

        try {
            if (!nome.trim().isEmpty())     encontrado.setNome(nome);
            if (!telefone.trim().isEmpty()) encontrado.setTelefone(telefone);
            if (!endereco.trim().isEmpty()) encontrado.setEndereco(endereco);
            clienteDAO.atualizar(encontrado);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void excluirCliente() {
        listarClientes();
        int id = lerInt("ID do cliente a excluir: ");
        System.out.print("Tem certeza? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            clienteDAO.excluir(id);
        } else {
            System.out.println("↩️  Exclusão cancelada.");
        }
    }

    // ─────────────────────────────────────────────
    //  MENU PRODUTOS (CRUD completo)
    // ─────────────────────────────────────────────

    static void menuProdutos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Excluir Produto");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
                case 3 -> atualizarProduto();
                case 4 -> excluirProduto();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }

    static void cadastrarProduto() {
        System.out.println("\n[ NOVO PRODUTO ]");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        double preco = lerDouble("Preço (ex: 19.90): R$");
        System.out.print("Categoria (Lanche/Bebida/Sobremesa/etc): ");
        String categoria = scanner.nextLine();

        try {
            Produto p = new Produto(0, nome, preco, categoria);
            produtoDAO.inserir(p);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void listarProdutos() {
        System.out.println("\n[ CARDÁPIO - LISTA DE PRODUTOS ]");
        List<Produto> lista = produtoDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto p : lista) System.out.println(p);
    }

    static void atualizarProduto() {
        listarProdutos();
        int id = lerInt("ID do produto a atualizar: ");
        Produto encontrado = produtoDAO.buscarPorId(id);
        if (encontrado == null) {
            System.out.println("❌ Produto não encontrado.");
            return;
        }

        System.out.println("\n[ ATUALIZAR PRODUTO ID: " + id + " ] (Enter para manter o valor atual)");
        System.out.print("Novo nome (atual: " + encontrado.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo preço (atual: R$" + String.format("%.2f", encontrado.getPreco()) + "): ");
        String precoStr = scanner.nextLine().trim().replace(",", ".");
        System.out.print("Nova categoria (atual: " + encontrado.getCategoria() + "): ");
        String categoria = scanner.nextLine();

        try {
            if (!nome.trim().isEmpty())      encontrado.setNome(nome);
            if (!precoStr.isEmpty())         encontrado.setPreco(Double.parseDouble(precoStr));
            if (!categoria.trim().isEmpty()) encontrado.setCategoria(categoria);
            produtoDAO.atualizar(encontrado);
        } catch (IllegalArgumentException | NumberFormatException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void excluirProduto() {
        listarProdutos();
        int id = lerInt("ID do produto a excluir: ");
        System.out.print("Tem certeza? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            produtoDAO.excluir(id);
        } else {
            System.out.println("↩️  Exclusão cancelada.");
        }
    }

    // ─────────────────────────────────────────────
    //  MENU ENTREGADORES (CRUD completo)
    // ─────────────────────────────────────────────

    static void menuEntregadores() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- ENTREGADORES ---");
            System.out.println("1. Cadastrar Entregador");
            System.out.println("2. Listar Entregadores");
            System.out.println("3. Atualizar Entregador");
            System.out.println("4. Alterar Disponibilidade");
            System.out.println("5. Excluir Entregador");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarEntregador();
                case 2 -> listarEntregadores();
                case 3 -> atualizarEntregador();
                case 4 -> alterarDisponibilidade();
                case 5 -> excluirEntregador();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }

    static void cadastrarEntregador() {
        System.out.println("\n[ NOVO ENTREGADOR ]");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Veículo (Moto/Bicicleta/Carro): ");
        String veiculo = scanner.nextLine();

        try {
            Entregador e = new Entregador(0, nome, veiculo, true);
            entregadorDAO.inserir(e);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void listarEntregadores() {
        System.out.println("\n[ LISTA DE ENTREGADORES ]");
        List<Entregador> lista = entregadorDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum entregador cadastrado.");
            return;
        }
        for (Entregador e : lista) System.out.println(e);
    }

    static void atualizarEntregador() {
        listarEntregadores();
        int id = lerInt("ID do entregador a atualizar: ");
        Entregador encontrado = entregadorDAO.buscarPorId(id);
        if (encontrado == null) {
            System.out.println("❌ Entregador não encontrado.");
            return;
        }

        System.out.println("\n[ ATUALIZAR ENTREGADOR ID: " + id + " ] (Enter para manter o valor atual)");
        System.out.print("Novo nome (atual: " + encontrado.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo veículo (atual: " + encontrado.getVeiculo() + "): ");
        String veiculo = scanner.nextLine();

        try {
            if (!nome.trim().isEmpty())    encontrado.setNome(nome);
            if (!veiculo.trim().isEmpty()) encontrado.setVeiculo(veiculo);
            entregadorDAO.atualizar(encontrado);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    static void alterarDisponibilidade() {
        listarEntregadores();
        List<Entregador> lista = entregadorDAO.listarTodos();
        if (lista.isEmpty()) return;
        int id = lerInt("ID do entregador para alterar disponibilidade: ");
        Entregador encontrado = entregadorDAO.buscarPorId(id);
        if (encontrado == null) {
            System.out.println("❌ Entregador não encontrado.");
            return;
        }
        encontrado.setDisponivel(!encontrado.isDisponivel());
        entregadorDAO.atualizar(encontrado);
        System.out.println("✅ Disponibilidade alterada! Agora: " + (encontrado.isDisponivel() ? "Disponível" : "Indisponível"));
    }

    static void excluirEntregador() {
        listarEntregadores();
        int id = lerInt("ID do entregador a excluir: ");
        System.out.print("Tem certeza? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            entregadorDAO.excluir(id);
        } else {
            System.out.println("↩️  Exclusão cancelada.");
        }
    }

    // ─────────────────────────────────────────────
    //  MENU PEDIDOS (CRUD completo — agora com banco!)
    // ─────────────────────────────────────────────

    static void menuPedidos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Criar Novo Pedido");
            System.out.println("2. Listar Todos os Pedidos");
            System.out.println("3. Avançar Status de Pedido");
            System.out.println("4. Buscar Pedidos por Cliente");
            System.out.println("5. Excluir Pedido");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> criarPedido();
                case 2 -> listarPedidos();
                case 3 -> avancarStatusPedido();
                case 4 -> buscarPedidosPorCliente();
                case 5 -> excluirPedido();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }

    static void criarPedido() {
        System.out.println("\n[ NOVO PEDIDO ]");

        List<Cliente> clientes = clienteDAO.listarTodos();
        List<Produto> produtos = produtoDAO.listarTodos();

        if (clientes.isEmpty()) {
            System.out.println("❌ Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }
        if (produtos.isEmpty()) {
            System.out.println("❌ Nenhum produto cadastrado. Cadastre produtos primeiro.");
            return;
        }

        // Selecionar cliente
        System.out.println("\n[ CLIENTES DISPONÍVEIS ]");
        for (Cliente c : clientes) System.out.println(c);
        int idCliente = lerInt("ID do cliente: ");
        Cliente cliente = clientes.stream()
                .filter(c -> c.getId() == idCliente)
                .findFirst().orElse(null);
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado.");
            return;
        }

        // Cria pedido com ID temporário 0 (o banco vai gerar o real)
        Pedido pedido = new Pedido(0, cliente);

        String continuar = "s";
        while (continuar.equalsIgnoreCase("s")) {
            System.out.println("\n[ PRODUTOS DISPONÍVEIS ]");
            for (Produto p : produtos) System.out.println(p);
            int idProduto = lerInt("ID do produto a adicionar: ");
            Produto produto = produtos.stream()
                    .filter(p -> p.getId() == idProduto)
                    .findFirst().orElse(null);
            if (produto == null) {
                System.out.println("❌ Produto não encontrado.");
            } else {
                int qtd = lerInt("Quantidade: ");
                try {
                    ItemPedido item = new ItemPedido(produto, qtd);
                    pedido.adicionarItem(item);
                    System.out.println("✅ Item adicionado! Subtotal: R$" + String.format("%.2f", item.getSubtotal()));
                } catch (IllegalArgumentException e) {
                    System.out.println("❌ Erro: " + e.getMessage());
                }
            }
            System.out.print("Adicionar outro item? (s/n): ");
            continuar = scanner.nextLine();
        }

        if (!pedido.isPedidoValido()) {
            System.out.println("❌ Pedido inválido (sem itens). Pedido cancelado.");
            return;
        }

        int idGerado = pedidoDAO.inserir(pedido);
        if (idGerado > 0) {
            System.out.println("\n✅ Pedido #" + idGerado + " criado com sucesso!");
            System.out.println("💰 Total: R$" + String.format("%.2f", pedido.calcularTotal()));
        }
    }

    static void listarPedidos() {
        System.out.println("\n[ LISTA DE PEDIDOS ]");
        List<Pedido> lista = pedidoDAO.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }
        for (Pedido p : lista) {
            System.out.println("─────────────────────────────");
            System.out.println(p);
        }
        System.out.println("─────────────────────────────");
    }

    static void avancarStatusPedido() {
        listarPedidos();
        List<Pedido> lista = pedidoDAO.listarTodos();
        if (lista.isEmpty()) return;
        int id = lerInt("ID do pedido para avançar status: ");
        Pedido pedido = pedidoDAO.buscarPorId(id);
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return;
        }
        Pedido.Status antes = pedido.getStatus();
        pedido.avancarStatus();
        pedidoDAO.atualizarStatus(pedido);
        System.out.println("✅ Status atualizado: " + antes + " → " + pedido.getStatus());
    }

    static void buscarPedidosPorCliente() {
        listarClientes();
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) return;
        int idCliente = lerInt("ID do cliente para buscar pedidos: ");
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado.");
            return;
        }
        System.out.println("\n[ PEDIDOS DE: " + cliente.getNome().toUpperCase() + " ]");
        List<Pedido> pedidos = pedidoDAO.listarPorCliente(idCliente);
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para este cliente.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println("─────────────────────────────");
            System.out.println(p);
        }
        System.out.println("─────────────────────────────");
    }

    static void excluirPedido() {
        listarPedidos();
        List<Pedido> lista = pedidoDAO.listarTodos();
        if (lista.isEmpty()) return;
        int id = lerInt("ID do pedido a excluir: ");
        System.out.print("Tem certeza? (s/n): ");
        if (scanner.nextLine().equalsIgnoreCase("s")) {
            pedidoDAO.excluir(id);
        } else {
            System.out.println("↩️  Exclusão cancelada.");
        }
    }

    // ─────────────────────────────────────────────
    //  MÉTODOS AUXILIARES DE LEITURA SEGURA
    // ─────────────────────────────────────────────

    static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite um número inteiro válido.");
            }
        }
    }

    static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite um valor numérico válido (ex: 19.90).");
            }
        }
    }
}
