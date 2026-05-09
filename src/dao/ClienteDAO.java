package dao;

import model.Cliente;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) responsável pelas operações de banco de dados da entidade Cliente.
 * Implementa CRUD completo usando PreparedStatement para evitar SQL Injection.
 * CP3: Padrão DAO com CRUD via JDBC.
 */
public class ClienteDAO {

    // ─────────────────────────────────────────────
    // CREATE — Inserir novo cliente
    // ─────────────────────────────────────────────

    /**
     * Insere um novo cliente no banco de dados.
     * @param cliente objeto Cliente a ser inserido
     */
    public void inserir(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, telefone, endereco) VALUES (?, ?, ?)";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.executeUpdate();

            // Recupera o ID gerado automaticamente pelo banco
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("✅ Cliente salvo no banco com ID: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir cliente: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // READ — Buscar por ID
    // ─────────────────────────────────────────────

    /**
     * Busca um cliente pelo ID.
     * @param id identificador do cliente
     * @return Cliente encontrado ou null
     */
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("endereco")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar cliente: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return cliente;
    }

    // ─────────────────────────────────────────────
    // READ — Listar todos
    // ─────────────────────────────────────────────

    /**
     * Retorna todos os clientes cadastrados no banco.
     * @return lista de clientes
     */
    public List<Cliente> listarTodos() {
        String sql = "SELECT * FROM clientes ORDER BY id";
        List<Cliente> lista = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getString("endereco")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar clientes: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // UPDATE — Atualizar cliente
    // ─────────────────────────────────────────────

    /**
     * Atualiza os dados de um cliente existente no banco.
     * @param cliente objeto Cliente com dados atualizados (usa o ID para localizar)
     */
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, telefone = ?, endereco = ? WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setInt(4, cliente.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Cliente atualizado com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum cliente encontrado com ID " + cliente.getId());
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar cliente: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // DELETE — Excluir cliente
    // ─────────────────────────────────────────────

    /**
     * Exclui um cliente do banco de dados pelo ID.
     * @param id identificador do cliente a ser excluído
     */
    public void excluir(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Cliente excluído com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum cliente encontrado com ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir cliente: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }
}
