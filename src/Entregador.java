public class Entregador extends Pessoa {

    private String veiculo;
    private boolean disponivel;

    // Construtor completo — chama super para id e nome
    public Entregador(int id, String nome, String veiculo, boolean disponivel) {
        super(id, nome);
        setVeiculo(veiculo);
        this.disponivel = disponivel;
    }

    // Getters
    public String getVeiculo() {
        return veiculo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // Setters com validação
    public void setVeiculo(String veiculo) {
        if (veiculo == null || veiculo.trim().isEmpty()) {
            throw new IllegalArgumentException("Veículo do entregador não pode ser vazio.");
        }
        this.veiculo = veiculo.trim();
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    // Sobrescrita de exibirInfo com comportamento específico
    @Override
    public void exibirInfo() {
        super.exibirInfo("Entregador:");
        System.out.println("  Veículo: " + veiculo);
        System.out.println("  Disponível: " + (disponivel ? "Sim" : "Não"));
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
