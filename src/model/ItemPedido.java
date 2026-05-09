package model;

/**
 * Representa um item dentro de um pedido, associando produto e quantidade.
 */
public class ItemPedido {

    private Produto produto;
    private int quantidade;

    // Construtor completo
    public ItemPedido(Produto produto, int quantidade) {
        setProduto(produto);
        setQuantidade(quantidade);
    }

    // Getters
    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // Regra de negócio: calcula subtotal deste item
    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    // Setters com validação
    public void setProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto do item não pode ser nulo.");
        }
        this.produto = produto;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "  - " + produto.getNome() +
                " x" + quantidade +
                " = R$" + String.format("%.2f", getSubtotal());
    }
}
