package model;

/**
 * Classe abstrata que representa uma pessoa no sistema.
 * Contém atributos e comportamentos comuns a todos os tipos de pessoa.
 * CP3: Transformada em abstrata com método abstrato exibirPermissoes().
 */
public abstract class Pessoa {

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

    // Métodos concretos (comportamento comum a todas as pessoas)
    public void exibirInfo() {
        System.out.println("Pessoa: " + nome);
    }

    public void exibirInfo(String prefixo) {
        System.out.println(prefixo + " " + nome);
    }

    public void exibirInfo(int id, String prefixo) {
        System.out.println(prefixo + " [ID: " + id + "] " + nome);
    }

    /**
     * Método abstrato: cada tipo de pessoa deve definir suas próprias permissões.
     * CP3: Método abstrato obrigatório.
     */
    public abstract void exibirPermissoes();

    @Override
    public String toString() {
        return "Pessoa{id=" + id + ", nome='" + nome + "'}";
    }
}
