package service;

import model.Pedido;
import util.Relatorio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorPedidoService implements Relatorio {

    private List<Pedido> pedidos;

    public GerenciadorPedidoService() {
        this.pedidos = new ArrayList<>();
    }

    public GerenciadorPedidoService(List<Pedido> pedidos) {
        this.pedidos = new ArrayList<>(pedidos);
    }

    public void adicionarPedido(Pedido pedido) {
        if (pedido != null) {
            pedidos.add(pedido);
        }
    }

    public List<Pedido> getPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Taxa de entrega baseada no valor final do pedido (após desconto):
     *   >= R$100 → grátis
     *   >= R$50  → R$5,00
     *   < R$50   → R$10,00
     */
    public double calcularTaxaEntrega(Pedido pedido) {
        double valorFinal = pedido.calcularValorFinal();
        if (valorFinal >= 100.0) {
            return 0.0;
        } else if (valorFinal >= 50.0) {
            return 5.0;
        } else {
            return 10.0;
        }
    }

    public double calcularValorComEntrega(Pedido pedido) {
        return pedido.calcularValorFinal() + calcularTaxaEntrega(pedido);
    }

    // --- Relatorio ---

    @Override
    public void gerarRelatorio() {
        System.out.println("============================================");
        System.out.println("          RELATÓRIO DE PEDIDOS              ");
        System.out.println("============================================");
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
                System.out.printf("  Taxa de Entrega: R$%.2f%n", calcularTaxaEntrega(pedido));
                System.out.printf("  Valor com Entrega: R$%.2f%n", calcularValorComEntrega(pedido));
                System.out.println("--------------------------------------------");
            }
        }
        System.out.println("============================================");
    }

    @Override
    public void exportarParaArquivo(String nomeArquivo) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("============================================\n");
            writer.write("          RELATÓRIO DE PEDIDOS              \n");
            writer.write("============================================\n");
            if (pedidos.isEmpty()) {
                writer.write("Nenhum pedido cadastrado.\n");
            } else {
                for (Pedido pedido : pedidos) {
                    writer.write(pedido.toString() + "\n");
                    writer.write(String.format("  Taxa de Entrega: R$%.2f%n", calcularTaxaEntrega(pedido)));
                    writer.write(String.format("  Valor com Entrega: R$%.2f%n", calcularValorComEntrega(pedido)));
                    writer.write("--------------------------------------------\n");
                }
            }
            writer.write("============================================\n");
            System.out.println("Relatorio exportado para: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao exportar relatorio: " + e.getMessage());
        }
    }
}
