package util;

import java.util.List;

public interface Auditavel {
    void registrarLog(String acao);
    List<String> obterHistorico();
}
