import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Listas que simulam o "banco de dados" em memória
    static List<Cliente> clientes = new ArrayList<>();
    static List<Produto> produtos = new ArrayList<>();
    static List<Entregador> entregadores = new ArrayList<>();
    static List<Pedido> pedidos = new ArrayList<>();

    // Contadores de ID
    static int contadorCliente = 1;
    static int contadorProduto = 1;
    static int contadorEntregador = 1;
    static int contadorPedido = 1;

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("   🍕 BEM-VINDO AO DELIVERYFÁCIL v2.0 🍕  ");
        System.out.println("============================================");

        // ─────────────────────────────────────────────
        // DEMONSTRAÇÃO DE POLIMORFISMO (CP2)
        // ─────────────────────────────────────────────
        System.out.println("\n===== DEMONSTRAÇÃO DE POLIMORFISMO =====");

        // ArrayList polimórfico: armazena diferentes subclasses de Pessoa
        ArrayList<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Cliente(1, "Maria Silva", "11999990000", "Rua das Flores, 10"));
        pessoas.add(new Entregador(2, "João Moto", "Moto", true));
        pessoas.add(new Administrador(3, "Carlos Admin", "Gerente"));

        // Polimorfismo: cada objeto chama sua versão de exibirInfo()
        for (Pessoa p : pessoas) {
            p.exibirInfo();
            System.out.println("---");
        }

        System.out.println("========================================\n");
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
    //  MENUS PRINCIPAIS
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
    //  MENU CLIENTES
    // ─────────────────────────────────────────────

    static void menuClientes() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> listarClientes();
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
            Cliente c = new Cliente(contadorCliente++, nome, telefone, endereco);
            clientes.add(c);
            System.out.println("✅ Cliente cadastrado com sucesso! ID: " + c.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
            contadorCliente--;
        }
    }

    static void listarClientes() {
        System.out.println("\n[ LISTA DE CLIENTES ]");
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    // ─────────────────────────────────────────────
    //  MENU PRODUTOS
    // ─────────────────────────────────────────────

    static void menuProdutos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- PRODUTOS ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarProduto();
                case 2 -> listarProdutos();
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
            Produto p = new Produto(contadorProduto++, nome, preco, categoria);
            produtos.add(p);
            System.out.println("✅ Produto cadastrado com sucesso! ID: " + p.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
            contadorProduto--;
        }
    }

    static void listarProdutos() {
        System.out.println("\n[ CARDÁPIO - LISTA DE PRODUTOS ]");
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

    // ─────────────────────────────────────────────
    //  MENU ENTREGADORES
    // ─────────────────────────────────────────────

    static void menuEntregadores() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- ENTREGADORES ---");
            System.out.println("1. Cadastrar Entregador");
            System.out.println("2. Listar Entregadores");
            System.out.println("3. Alterar Disponibilidade");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> cadastrarEntregador();
                case 2 -> listarEntregadores();
                case 3 -> alterarDisponibilidade();
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
            Entregador e = new Entregador(contadorEntregador++, nome, veiculo, true);
            entregadores.add(e);
            System.out.println("✅ Entregador cadastrado com sucesso! ID: " + e.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
            contadorEntregador--;
        }
    }

    static void listarEntregadores() {
        System.out.println("\n[ LISTA DE ENTREGADORES ]");
        if (entregadores.isEmpty()) {
            System.out.println("Nenhum entregador cadastrado.");
            return;
        }
        for (Entregador e : entregadores) {
            System.out.println(e);
        }
    }

    static void alterarDisponibilidade() {
        listarEntregadores();
        if (entregadores.isEmpty()) return;
        int id = lerInt("ID do entregador para alterar disponibilidade: ");
        Entregador encontrado = buscarEntregadorPorId(id);
        if (encontrado == null) {
            System.out.println("❌ Entregador não encontrado.");
            return;
        }
        encontrado.setDisponivel(!encontrado.isDisponivel());
        System.out.println("✅ Disponibilidade alterada! Agora: " + (encontrado.isDisponivel() ? "Disponível" : "Indisponível"));
    }

    // ─────────────────────────────────────────────
    //  MENU PEDIDOS
    // ─────────────────────────────────────────────

    static void menuPedidos() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- PEDIDOS ---");
            System.out.println("1. Criar Novo Pedido");
            System.out.println("2. Listar Todos os Pedidos");
            System.out.println("3. Avançar Status de Pedido");
            System.out.println("4. Buscar Pedidos por Cliente");
            System.out.println("0. Voltar");
            opcao = lerInt("Opção: ");

            switch (opcao) {
                case 1 -> criarPedido();
                case 2 -> listarPedidos();
                case 3 -> avancarStatusPedido();
                case 4 -> buscarPedidosPorCliente();
                case 0 -> System.out.println("↩️  Voltando...");
                default -> System.out.println("❌ Opção inválida!");
            }
        }
    }

    static void criarPedido() {
        System.out.println("\n[ NOVO PEDIDO ]");

        if (clientes.isEmpty()) {
            System.out.println("❌ Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
            return;
        }
        if (produtos.isEmpty()) {
            System.out.println("❌ Nenhum produto cadastrado. Cadastre produtos primeiro.");
            return;
        }

        listarClientes();
        int idCliente = lerInt("ID do cliente: ");
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado.");
            return;
        }

        Pedido pedido = new Pedido(contadorPedido++, cliente);

        String continuar = "s";
        while (continuar.equalsIgnoreCase("s")) {
            listarProdutos();
            int idProduto = lerInt("ID do produto a adicionar: ");
            Produto produto = buscarProdutoPorId(idProduto);
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
            contadorPedido--;
            return;
        }

        pedidos.add(pedido);
        System.out.println("\n✅ Pedido #" + pedido.getId() + " criado com sucesso!");
        System.out.println("💰 Total: R$" + String.format("%.2f", pedido.calcularTotal()));
    }

    static void listarPedidos() {
        System.out.println("\n[ LISTA DE PEDIDOS ]");
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println("─────────────────────────────");
            System.out.println(p);
        }
        System.out.println("─────────────────────────────");
    }

    static void avancarStatusPedido() {
        listarPedidos();
        if (pedidos.isEmpty()) return;
        int id = lerInt("ID do pedido para avançar status: ");
        Pedido pedido = buscarPedidoPorId(id);
        if (pedido == null) {
            System.out.println("❌ Pedido não encontrado.");
            return;
        }
        Pedido.Status antes = pedido.getStatus();
        pedido.avancarStatus();
        System.out.println("✅ Status atualizado: " + antes + " → " + pedido.getStatus());
    }

    static void buscarPedidosPorCliente() {
        listarClientes();
        if (clientes.isEmpty()) return;
        int idCliente = lerInt("ID do cliente para buscar pedidos: ");
        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado.");
            return;
        }
        System.out.println("\n[ PEDIDOS DE: " + cliente.getNome().toUpperCase() + " ]");
        boolean encontrou = false;
        for (Pedido p : pedidos) {
            if (p.getCliente().getId() == idCliente) {
                System.out.println(p);
                System.out.println("─────────────────────────────");
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum pedido encontrado para este cliente.");
        }
    }

    // ─────────────────────────────────────────────
    //  MÉTODOS AUXILIARES DE BUSCA
    // ─────────────────────────────────────────────

    static Cliente buscarClientePorId(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    static Produto buscarProdutoPorId(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    static Entregador buscarEntregadorPorId(int id) {
        for (Entregador e : entregadores) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    static Pedido buscarPedidoPorId(int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) return p;
        }
        return null;
    }

    // ─────────────────────────────────────────────
    //  MÉTODOS AUXILIARES DE LEITURA SEGURA
    // ─────────────────────────────────────────────

    static int lerInt(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite um número inteiro válido.");
            }
        }
    }

    static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite um valor numérico válido (ex: 19.90).");
            }
        }
    }
}
