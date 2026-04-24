public class Pessoa {

    protected int id;
    protected String nome;

    // Construtor
    public Pessoa(int id, String nome) {
        this.id = id;
        setNome(nome);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    // Setter com validação
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    // Sobrecarga de exibirInfo
    public void exibirInfo() {
        System.out.println("Pessoa: " + nome);
    }

    public void exibirInfo(String prefixo) {
        System.out.println(prefixo + " " + nome);
    }

    public void exibirInfo(int id, String prefixo) {
        System.out.println(prefixo + " [ID: " + id + "] " + nome);
    }

    @Override
    public String toString() {
        return "Pessoa{id=" + id + ", nome='" + nome + "'}";
    }
}
