package com.taligado.energy.service;

import com.taligado.energy.dto.EmpresaDTO;
import com.taligado.energy.dto.EnderecoDTO;
import com.taligado.energy.utils.FormatData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class ProceduresService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para chamar a procedure de inserção de empresa
    public String inserirEmpresaProcedure(EmpresaDTO empresaDTO) {
        try {

            // Formatar a data de fundação
            String dataFundacaoFormatada = FormatData.formatarDataFundacao(String.valueOf(empresaDTO.getDataFundacao()));

            // O formato correto do Oracle é dd/MM/yy
            SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yy");
            java.util.Date dataFundacao = sdfEntrada.parse(dataFundacaoFormatada);
            java.sql.Date dataFundacaoSql = new java.sql.Date(dataFundacao.getTime());

            // Preparar o SQL para chamar a procedure PL/SQL
            String sql = "BEGIN " +
                    "pkg_insercao_dados.inserir_empresa(?, ?, ?, ?, ?, ?); " +
                    "END;";

            // Executando o SQL de chamada à procedure
            int rowsAffected = jdbcTemplate.update(sql,
                    empresaDTO.getIdempresa(),
                    empresaDTO.getNome(),
                    empresaDTO.getEmail(),
                    empresaDTO.getCnpj(),
                    empresaDTO.getSegmento(),
                    dataFundacaoSql
            );

            if (rowsAffected > 0) {
                return "Empresa criada com sucesso via PROCEDURE!";
            } else {
                throw new RuntimeException("Nenhuma linha foi inserida.");
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar empresa via PROCEDURE: " + e.getMessage(), e);
        }
    }

    public String inserirEnderecoProcedure(EnderecoDTO enderecoDTO) {
        try {
            // Preparar o SQL para chamar a procedure PL/SQL
            String sql = "BEGIN " +
                    "pkg_insercao_dados.inserir_endereco(?, ?, ?, ?, ?, ?); " +
                    "END;";

            // Executando o SQL de chamada à procedure
            int rowsAffected = jdbcTemplate.update(sql,
                    enderecoDTO.getIdendereco(),
                    enderecoDTO.getLogadouro(),
                    enderecoDTO.getCidade(),
                    enderecoDTO.getEstado(),
                    enderecoDTO.getCep(),
                    enderecoDTO.getPais()
            );

            // Verifica se a inserção foi bem-sucedida
            if (rowsAffected > 0) {
                return "Endereço criado com sucesso via PROCEDURE!";
            } else {
                // Lança um erro específico se não houver inserção
                throw new RuntimeException("Nenhuma linha foi inserida.");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Já existe um endereço com este ID")) {
                throw new RuntimeException("Erro: Já existe um endereço com este ID.");
            } else if (errorMessage.contains("Erro de tipo de dados ao inserir endereço")) {
                throw new RuntimeException("Erro: Verifique os tipos de dados.");
            } else {
                throw new RuntimeException("Erro desconhecido ao inserir endereço: " + e.getMessage(), e);
            }
        }
    }


}
