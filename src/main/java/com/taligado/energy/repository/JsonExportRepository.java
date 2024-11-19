package com.taligado.energy.repository;

import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class JsonExportRepository {

    private final JdbcTemplate jdbcTemplate;

    public JsonExportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Clob gerarJson() {
        // Chama a procedure e captura o Clob gerado
        return jdbcTemplate.execute((ConnectionCallback<Clob>) connection -> {
            String sql = "{call pkg_exportacao.gerar_json(?)}"; // Passa o parâmetro de saída
            try (CallableStatement cs = connection.prepareCall(sql)) {
                cs.registerOutParameter(1, Types.CLOB); // Registrando o parâmetro de saída como CLOB
                cs.execute();
                return cs.getClob(1); // Retorna o CLOB gerado
            }
        });
    }
}
