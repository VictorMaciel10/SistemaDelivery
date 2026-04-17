public class Produto {

    private int id;
    private String nome;
    private double preco;
    private String categoria; // Ex: "Lanche", "Bebida", "Sobremesa"

    // Construtor completo
    public Produto(int id, String nome, double preco, String categoria) {
        this.id = id;
        setNome(nome);
        setPreco(preco);
        setCategoria(categoria);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public String getCategoria() {
        return categoria;
    }

    // Setters com validação
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public void setPreco(double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero.");
        }
        this.preco = preco;
    }

    public void setCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Categoria do produto não pode ser vazia.");
        }
        this.categoria = categoria.trim();
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", preco=R$" + String.format("%.2f", preco) +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
