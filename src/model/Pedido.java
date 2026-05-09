package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa um pedido feito por um cliente no sistema de delivery.
 */
public class Pedido {

    // Enum para os status possíveis do pedido (sequencial)
    public enum Status {
        AGUARDANDO,
        EM_PREPARO,
        SAIU_PARA_ENTREGA,
        ENTREGUE
    }

    private int id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private Status status;

    // Construtor — pedido começa sempre como AGUARDANDO
    public Pedido(int id, Cliente cliente) {
        setCliente(cliente);
        this.id = id;
        this.itens = new ArrayList<>();
        this.status = Status.AGUARDANDO;
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

    // Adiciona um item ao pedido
    public void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo.");
        }
        itens.add(item);
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
                break;
        }
    }

    // Regra de negócio: calcula o total somando todos os itens
    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }

    // Valida se o pedido pode ser finalizado
    public boolean isPedidoValido() {
        return cliente != null && !itens.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido #").append(id)
          .append(" | Cliente: ").append(cliente.getNome())
          .append(" | Status: ").append(status)
          .append("\n  Itens:\n");
        for (ItemPedido item : itens) {
            sb.append(item.toString()).append("\n");
        }
        sb.append("  TOTAL: R$").append(String.format("%.2f", calcularTotal()));
        return sb.toString();
    }
}
