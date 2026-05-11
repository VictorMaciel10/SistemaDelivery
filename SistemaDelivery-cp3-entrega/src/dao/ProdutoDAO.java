package dao;

import model.Produto;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações de banco de dados da entidade Produto.
 * Implementa CRUD completo usando PreparedStatement para evitar SQL Injection.
 * CP3: Padrão DAO com CRUD via JDBC.
 */
public class ProdutoDAO {

    // ─────────────────────────────────────────────
    // CREATE — Inserir novo produto
    // ─────────────────────────────────────────────

    /**
     * Insere um novo produto no banco de dados.
     * @param produto objeto Produto a ser inserido
     */
    public void inserir(Produto produto) {
        String sql = "INSERT INTO produtos (nome, preco, categoria) VALUES (?, ?, ?)";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getCategoria());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                System.out.println("✅ Produto salvo no banco com ID: " + rs.getInt(1));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir produto: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // READ — Buscar por ID
    // ─────────────────────────────────────────────

    /**
     * Busca um produto pelo ID.
     * @param id identificador do produto
     * @return Produto encontrado ou null
     */
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("categoria")
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar produto: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return produto;
    }

    // ─────────────────────────────────────────────
    // READ — Listar todos
    // ─────────────────────────────────────────────

    /**
     * Retorna todos os produtos cadastrados no banco.
     * @return lista de produtos
     */
    public List<Produto> listarTodos() {
        String sql = "SELECT * FROM produtos ORDER BY id";
        List<Produto> lista = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(new Produto(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDouble("preco"),
                    rs.getString("categoria")
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar produtos: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // UPDATE — Atualizar produto
    // ─────────────────────────────────────────────

    /**
     * Atualiza os dados de um produto existente no banco.
     * @param produto objeto Produto com dados atualizados
     */
    public void atualizar(Produto produto) {
        String sql = "UPDATE produtos SET nome = ?, preco = ?, categoria = ? WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setString(3, produto.getCategoria());
            stmt.setInt(4, produto.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Produto atualizado com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum produto encontrado com ID " + produto.getId());
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar produto: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // DELETE — Excluir produto
    // ─────────────────────────────────────────────

    /**
     * Exclui um produto do banco de dados pelo ID.
     * @param id identificador do produto a ser excluído
     */
    public void excluir(int id) {
        String sql = "DELETE FROM produtos WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Produto excluído com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum produto encontrado com ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir produto: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }
}
