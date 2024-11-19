package com.taligado.energy.service;

import com.taligado.energy.repository.JsonExportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Clob;

@Service
public class JsonExportService {

    @Autowired
    private JsonExportRepository jsonExportRepository;

    public String exportarJson() {
        try {
            // Executa a procedure e obtém o JSON como CLOB
            Clob jsonClob = jsonExportRepository.gerarJson();

            // Verifica se o CLOB não é nulo antes de processar
            if (jsonClob == null) {
                throw new IllegalStateException("Nenhum JSON foi retornado pela procedure.");
            }

            // Converte o CLOB para String
            return jsonClob.getSubString(1, (int) jsonClob.length());
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"erro\": \"Erro ao gerar JSON: " + e.getMessage() + "\"}";
        }
    }

}
