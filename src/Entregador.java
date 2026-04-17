public class Entregador {

    private int id;
    private String nome;
    private String veiculo;     // Ex: "Moto", "Bicicleta", "Carro"
    private boolean disponivel;

    // Construtor completo
    public Entregador(int id, String nome, String veiculo, boolean disponivel) {
        this.id = id;
        setNome(nome);
        setVeiculo(veiculo);
        this.disponivel = disponivel;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // Setters com validação
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do entregador não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public void setVeiculo(String veiculo) {
        if (veiculo == null || veiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("Veículo do entregador não pode ser vazio.");
        }
        this.veiculo = veiculo.trim();
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Entregador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", veiculo='" + veiculo + '\'' +
                ", disponivel=" + (disponivel ? "Sim" : "Não") +
                '}';
    }
}
