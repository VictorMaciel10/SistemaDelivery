package model;

/**
 * Representa um administrador do sistema de delivery.
 * Herda de Pessoa e implementa o método abstrato exibirPermissoes().
 */
public class Administrador extends Pessoa {

    private String cargo;

    // Construtor completo — chama super para id e nome
    public Administrador(int id, String nome, String cargo) {
        super(id, nome);
        setCargo(cargo);
    }

    // Getter
    public String getCargo() {
        return cargo;
    }

    // Setter com validação
    public void setCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty()) {
            throw new IllegalArgumentException("Cargo do administrador não pode ser vazio.");
        }
        this.cargo = cargo.trim();
    }

    // Sobrescrita de exibirInfo com comportamento específico
    @Override
    public void exibirInfo() {
        super.exibirInfo("Administrador:");
        System.out.println("  Cargo: " + cargo);
    }

    // CP3: Implementação obrigatória do método abstrato
    @Override
    public void exibirPermissoes() {
        System.out.println("Permissões de Administrador: acesso total ao sistema, gerenciar usuários e produtos.");
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                '}';
    }
}
