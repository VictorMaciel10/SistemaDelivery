package dao;

import model.Cliente;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsável pelas operações de banco de dados da entidade Pedido.
 * Gerencia também os itens do pedido na tabela itens_pedido.
 * Implementa CRUD completo usando PreparedStatement para evitar SQL Injection.
 * CP3: Padrão DAO com CRUD via JDBC.
 */
public class PedidoDAO {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    // ─────────────────────────────────────────────
    // CREATE — Inserir novo pedido com seus itens
    // ─────────────────────────────────────────────

    /**
     * Insere um pedido e todos os seus itens no banco de dados.
     * Usa transação para garantir que pedido e itens sejam salvos juntos.
     * @param pedido objeto Pedido a ser inserido
     * @return ID gerado pelo banco, ou -1 em caso de erro
     */
    public int inserir(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedidos (cliente_id, status) VALUES (?, ?)";
        String sqlItem   = "INSERT INTO itens_pedido (pedido_id, produto_id, quantidade, subtotal) VALUES (?, ?, ?, ?)";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            conexao.setAutoCommit(false); // inicia transação

            // Insere o pedido
            PreparedStatement stmtPedido = conexao.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            stmtPedido.setInt(1, pedido.getCliente().getId());
            stmtPedido.setString(2, pedido.getStatus().name());
            stmtPedido.executeUpdate();

            ResultSet rs = stmtPedido.getGeneratedKeys();
            if (!rs.next()) {
                conexao.rollback();
                return -1;
            }
            int idGerado = rs.getInt(1);

            // Insere cada item do pedido
            PreparedStatement stmtItem = conexao.prepareStatement(sqlItem);
            for (ItemPedido item : pedido.getItens()) {
                stmtItem.setInt(1, idGerado);
                stmtItem.setInt(2, item.getProduto().getId());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, item.getSubtotal());
                stmtItem.addBatch();
            }
            stmtItem.executeBatch();

            conexao.commit(); // confirma transação
            System.out.println("✅ Pedido salvo no banco com ID: " + idGerado);
            return idGerado;

        } catch (SQLException e) {
            System.out.println("❌ Erro ao inserir pedido: " + e.getMessage());
            try {
                if (conexao != null) conexao.rollback();
            } catch (SQLException ex) {
                System.out.println("❌ Erro ao desfazer transação: " + ex.getMessage());
            }
            return -1;
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // READ — Buscar pedido por ID (com seus itens)
    // ─────────────────────────────────────────────

    /**
     * Busca um pedido pelo ID, carregando também seus itens.
     * @param id identificador do pedido
     * @return Pedido encontrado ou null
     */
    public Pedido buscarPorId(int id) {
        String sqlPedido = "SELECT * FROM pedidos WHERE id = ?";
        String sqlItens  = "SELECT * FROM itens_pedido WHERE pedido_id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();

            // Busca o pedido
            PreparedStatement stmtPedido = conexao.prepareStatement(sqlPedido);
            stmtPedido.setInt(1, id);
            ResultSet rsPedido = stmtPedido.executeQuery();

            if (!rsPedido.next()) return null;

            Cliente cliente = clienteDAO.buscarPorId(rsPedido.getInt("cliente_id"));
            Pedido pedido = new Pedido(rsPedido.getInt("id"), cliente);
            pedido.setStatus(Pedido.Status.valueOf(rsPedido.getString("status")));

            // Busca os itens do pedido
            PreparedStatement stmtItens = conexao.prepareStatement(sqlItens);
            stmtItens.setInt(1, id);
            ResultSet rsItens = stmtItens.executeQuery();

            while (rsItens.next()) {
                Produto produto = produtoDAO.buscarPorId(rsItens.getInt("produto_id"));
                ItemPedido item = new ItemPedido(produto, rsItens.getInt("quantidade"));
                pedido.adicionarItem(item);
            }

            return pedido;

        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar pedido: " + e.getMessage());
            return null;
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // READ — Listar todos os pedidos
    // ─────────────────────────────────────────────

    /**
     * Retorna todos os pedidos cadastrados no banco, com seus itens.
     * @return lista de pedidos
     */
    public List<Pedido> listarTodos() {
        String sql = "SELECT id FROM pedidos ORDER BY id";
        List<Pedido> lista = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = buscarPorId(rs.getInt("id"));
                if (p != null) lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao listar pedidos: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // READ — Listar pedidos por cliente
    // ─────────────────────────────────────────────

    /**
     * Retorna todos os pedidos de um cliente específico.
     * @param clienteId identificador do cliente
     * @return lista de pedidos do cliente
     */
    public List<Pedido> listarPorCliente(int clienteId) {
        String sql = "SELECT id FROM pedidos WHERE cliente_id = ? ORDER BY id";
        List<Pedido> lista = new ArrayList<>();

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pedido p = buscarPorId(rs.getInt("id"));
                if (p != null) lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao buscar pedidos do cliente: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
        return lista;
    }

    // ─────────────────────────────────────────────
    // UPDATE — Atualizar status do pedido
    // ─────────────────────────────────────────────

    /**
     * Atualiza o status de um pedido no banco de dados.
     * @param pedido objeto Pedido com o novo status
     */
    public void atualizarStatus(Pedido pedido) {
        String sql = "UPDATE pedidos SET status = ? WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, pedido.getStatus().name());
            stmt.setInt(2, pedido.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("✅ Status do pedido atualizado no banco!");
            } else {
                System.out.println("⚠️  Nenhum pedido encontrado com ID " + pedido.getId());
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao atualizar status: " + e.getMessage());
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }

    // ─────────────────────────────────────────────
    // DELETE — Excluir pedido e seus itens
    // ─────────────────────────────────────────────

    /**
     * Exclui um pedido e todos os seus itens do banco de dados.
     * Usa transação para garantir que ambos sejam excluídos juntos.
     * @param id identificador do pedido a ser excluído
     */
    public void excluir(int id) {
        String sqlItens  = "DELETE FROM itens_pedido WHERE pedido_id = ?";
        String sqlPedido = "DELETE FROM pedidos WHERE id = ?";

        Connection conexao = null;
        try {
            conexao = ConexaoBD.getConexao();
            conexao.setAutoCommit(false); // inicia transação

            // Primeiro exclui os itens (por causa da chave estrangeira)
            PreparedStatement stmtItens = conexao.prepareStatement(sqlItens);
            stmtItens.setInt(1, id);
            stmtItens.executeUpdate();

            // Depois exclui o pedido
            PreparedStatement stmtPedido = conexao.prepareStatement(sqlPedido);
            stmtPedido.setInt(1, id);
            int linhasAfetadas = stmtPedido.executeUpdate();

            conexao.commit();

            if (linhasAfetadas > 0) {
                System.out.println("✅ Pedido excluído com sucesso!");
            } else {
                System.out.println("⚠️  Nenhum pedido encontrado com ID " + id);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erro ao excluir pedido: " + e.getMessage());
            try {
                if (conexao != null) conexao.rollback();
            } catch (SQLException ex) {
                System.out.println("❌ Erro ao desfazer transação: " + ex.getMessage());
            }
        } finally {
            ConexaoBD.fecharConexao(conexao);
        }
    }
}
