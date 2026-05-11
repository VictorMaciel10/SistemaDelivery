package model;

import util.Auditavel;
import util.Calculavel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Auditavel, Calculavel {

    public enum Status {
        AGUARDANDO,
        EM_PREPARO,
        SAIU_PARA_ENTREGA,
        ENTREGUE
    }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private Status status;
    private List<String> historico;

    public Pedido(int id, Cliente cliente) {
        setCliente(cliente);
        this.id = id;
        this.itens = new ArrayList<>();
        this.status = Status.AGUARDANDO;
        this.historico = new ArrayList<>();
        registrarLog("Pedido #" + id + " criado para o cliente " + cliente.getNome());
    }

    // Getters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Status getStatus() {
        return status;
    }

    // Setters com validação
    public void setStatus(Status status) {
        if (status != null) this.status = status;
    }

    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Pedido deve ter um cliente válido.");
        }
        this.cliente = cliente;
    }

    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo.");
        }
        itens.add(item);
        registrarLog("Item adicionado: " + item.getProduto().getNome()
                + " (qtd: " + item.getQuantidade() + ")");
    }

    // Regra de negócio: avança status de forma sequencial
    public void avancarStatus() {
        switch (status) {
            case AGUARDANDO:
                status = Status.EM_PREPARO;
                break;
            case EM_PREPARO:
                status = Status.SAIU_PARA_ENTREGA;
                break;
            case SAIU_PARA_ENTREGA:
                status = Status.ENTREGUE;
                break;
            case ENTREGUE:
                System.out.println("⚠️  Este pedido já foi entregue. Não há próximo status.");
                return;
        }
        registrarLog("Status avançado para: " + status);
    }

    // Valida se o pedido pode ser finalizado
    public boolean isPedidoValido() {
        return cliente != null && !itens.isEmpty();
    }

    // --- Auditavel ---

    @Override
    public void registrarLog(String acao) {
        String entrada = "[" + LocalDateTime.now().format(FORMATTER) + "] " + acao;
        historico.add(entrada);
    }

    @Override
    public List<String> obterHistorico() {
        return new ArrayList<>(historico);
    }

    // --- Calculavel ---

    @Override
    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    /**
     * Desconto progressivo:
     *   total >= R$100 → 10%
     *   total >= R$50  → 5%
     *   total < R$50   → 0%
     */
    @Override
    public double calcularDesconto() {
        double total = calcularTotal();
        if (total >= 100.0) {
            return total * 0.10;
        } else if (total >= 50.0) {
            return total * 0.05;
        }
        return 0.0;
    }

    @Override
    public double calcularValorFinal() {
        return calcularTotal() - calcularDesconto();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id)
          .append(" | Cliente: ").append(cliente.getNome())
          .append(" | Status: ").append(status)
          .append("\n  Itens:\n");
        for (ItemPedido item : itens) {
            sb.append("    ").append(item.toString()).append("\n");
        }
        sb.append("  Total: R$").append(String.format("%.2f", calcularTotal()))
          .append(" | Desconto: R$").append(String.format("%.2f", calcularDesconto()))
          .append(" | Valor Final: R$").append(String.format("%.2f", calcularValorFinal()));
        return sb.toString();
    }
}
