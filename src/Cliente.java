public class Cliente extends Pessoa {

    private String telefone;
    private String endereco;

    // Construtor completo — chama super para id e nome
    public Cliente(int id, String nome, String telefone, String endereco) {
        super(id, nome);
        setTelefone(telefone);
        setEndereco(endereco);
    }

    // Getters
    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    // Setters com validação
    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefone do cliente não pode ser vazio.");
        }
        this.telefone = telefone.trim();
    }

    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("Endereço do cliente não pode ser vazio.");
        }
        this.endereco = endereco.trim();
    }

    // Sobrescrita de exibirInfo com comportamento específico
    @Override
    public void exibirInfo() {
        super.exibirInfo("Cliente:");
        System.out.println("  Telefone: " + telefone);
        System.out.println("  Endereço: " + endereco);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
