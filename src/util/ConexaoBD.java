package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária responsável por gerenciar a conexão com o banco de dados PostgreSQL.
 * Utiliza o padrão de conexão via JDBC.
 * CP3: Classe de conexão JDBC.
 */
public class ConexaoBD {

    // ───── Configurações de conexão ─────
    // Altere estes valores conforme o seu ambiente PostgreSQL
    private static final String URL      = "jdbc:postgresql://localhost:5432/delivery_db";
    private static final String USUARIO  = "postgres";
    private static final String SENHA    = "postgres";

    /**
     * Retorna uma nova conexão com o banco de dados.
     * @return Connection ativa
     * @throws SQLException se não conseguir conectar
     */
    public static Connection getConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    /**
     * Fecha a conexão com segurança, evitando erros de null.
     * @param conexao conexão a ser fechada
     */
    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (SQLException e) {
                System.out.println("⚠️  Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
