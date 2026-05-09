package dao;

import model.Entregador;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações de banco de dados da entidade Entregador.
 * Implementa CRUD completo usando PreparedStatement para evitar SQL Injection.
 * CP3: Padrão DAO com CRUD via JDBC.
 */
public class EntregadorDAO {

    // ─────────────────────────────────────────────
    // CREATE — Inserir novo entregador
    // ─────────────────────────────────────────────

    /**
     * Insere um novo entregador no banco de dados.
     * @param entregador objeto Entregador a ser inserido
     */
    public void inserir(Entregador entregador) {
        String sql = "INSERT INTO entregadores (nome, veiculo, disponivel) VALUES (?, ?, ?)";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getVeiculo());
            stmt.setBoolean(3, entregador.isDisponivel());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("✅ Entregador salvo no banco com ID: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir entregador: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // READ — Buscar por ID
    // ─────────────────────────────────────────────

    /**
     * Busca um entregador pelo ID.
     * @param id identificador do entregador
     * @return Entregador encontrado ou null
     */
    public Entregador buscarPorId(int id) {
        String sql = "SELECT * FROM entregadores WHERE id = ?";
        Entregador entregador = null;

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                entregador = new Entregador(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("veiculo"),
                    rs.getBoolean("disponivel")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar entregador: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return entregador;
    }

    // ─────────────────────────────────────────────
    // READ — Listar todos
    // ─────────────────────────────────────────────

    /**
     * Retorna todos os entregadores cadastrados no banco.
     * @return lista de entregadores
     */
    public List<Entregador> listarTodos() {
        String sql = "SELECT * FROM entregadores ORDER BY id";
        List<Entregador> lista = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Entregador(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("veiculo"),
                    rs.getBoolean("disponivel")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar entregadores: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // UPDATE — Atualizar entregador
    // ─────────────────────────────────────────────

    /**
     * Atualiza os dados de um entregador existente no banco.
     * @param entregador objeto Entregador com dados atualizados
     */
    public void atualizar(Entregador entregador) {
        String sql = "UPDATE entregadores SET nome = ?, veiculo = ?, disponivel = ? WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, entregador.getNome());
            stmt.setString(2, entregador.getVeiculo());
            stmt.setBoolean(3, entregador.isDisponivel());
            stmt.setInt(4, entregador.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Entregador atualizado com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum entregador encontrado com ID " + entregador.getId());
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar entregador: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // DELETE — Excluir entregador
    // ─────────────────────────────────────────────

    /**
     * Exclui um entregador do banco de dados pelo ID.
     * @param id identificador do entregador a ser excluído
     */
    public void excluir(int id) {
        String sql = "DELETE FROM entregadores WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Entregador excluído com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum entregador encontrado com ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir entregador: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }
}
