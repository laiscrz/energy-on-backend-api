package com.taligado.energy.service;

import com.taligado.energy.dto.*;
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
            String dataFundacaoFormatada = FormatData.formatarData(String.valueOf(empresaDTO.getDataFundacao()));

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

    public String inserirFilialProcedure(FilialDTO filialDTO) {
        try {
            // Preparar o SQL para chamar a procedure PL/SQL
            String sql = "BEGIN " +
                    "pkg_insercao_dados.inserir_filial(?, ?, ?, ?, ?, ?, ?); " +
                    "END;";

            // Executando o SQL de chamada à procedure
            int rowsAffected = jdbcTemplate.update(sql,
                    filialDTO.getIdfilial(),
                    filialDTO.getNome(),
                    filialDTO.getTipo(),
                    filialDTO.getCnpjFilial(),
                    filialDTO.getAreaOperacional(),
                    filialDTO.getEmpresaId(),      // ID da empresa
                    filialDTO.getEnderecoId()      // ID do endereço
            );

            // Verifica se a inserção foi bem-sucedida
            if (rowsAffected > 0) {
                return "Filial criada com sucesso via PROCEDURE!";
            } else {
                throw new RuntimeException("Nenhuma linha foi inserida.");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Já existe uma filial com este ID")) {
                throw new RuntimeException("Erro: Já existe uma filial com este ID.");
            } else if (errorMessage.contains("Erro de tipo de dados ao inserir filial")) {
                throw new RuntimeException("Erro: Verifique os tipos de dados.");
            } else {
                throw new RuntimeException("Erro desconhecido ao inserir filial: " + e.getMessage(), e);
            }
        }
    }

    public String inserirRegulacaoEnergiaProcedure(RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        try {
            String dataAtualizacaoFormatada = FormatData.formatarData(String.valueOf(regulacaoEnergiaDTO.getDataAtualizacao()));

            // Formatação de data para o formato correto
            SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yy");
            java.util.Date dataAtualizacao = sdfEntrada.parse(dataAtualizacaoFormatada);
            java.sql.Date dataAtualizacaoSql = new java.sql.Date(dataAtualizacao.getTime());

            // Preparar o SQL para chamar a procedure PL/SQL
            String sql = "BEGIN " +
                    "pkg_insercao_dados.inserir_regulacao_energia(?, ?, ?, ?, ?); " +
                    "END;";

            // Executando o SQL de chamada à procedure
            int rowsAffected = jdbcTemplate.update(sql,
                    regulacaoEnergiaDTO.getIdregulacao(),
                    regulacaoEnergiaDTO.getTarifaKwh(),
                    regulacaoEnergiaDTO.getNomeBandeira(),
                    regulacaoEnergiaDTO.getTarifaAdicionalBandeira(),
                    dataAtualizacaoSql
            );

            // Verifica se a inserção foi bem-sucedida
            if (rowsAffected > 0) {
                System.out.println("Regulação de energia criada com sucesso!");
                return "Regulação de energia criada com sucesso via PROCEDURE!";
            } else {
                System.out.println("Nenhuma linha foi inserida.");
                throw new RuntimeException("Nenhuma linha foi inserida.");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Já existe uma regulaçaão de energia com este ID")) {
                throw new RuntimeException("Erro: Já existe uma regulaçaão de energia com este ID.");
            } else if (errorMessage.contains("Erro de tipo de dados ao inserir regulaçaão de energia")) {
                throw new RuntimeException("Erro: Verifique os tipos de dados.");
            } else {
                throw new RuntimeException("Erro desconhecido ao inserir regulaçaão de energia: " + e.getMessage(), e);
            }
        }
    }

    public String inserirAlertaProcedure(AlertaDTO alertaDTO) {
        try {
            String dataAlertaFormatada = FormatData.formatarData(String.valueOf(alertaDTO.getDataAlerta()));

            // Formatação de data para o formato correto
            SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yy");
            java.util.Date dataAlerta = sdfEntrada.parse(dataAlertaFormatada);
            java.sql.Date dataAlertaSql = new java.sql.Date(dataAlerta.getTime());
            // Preparar o SQL para chamar a procedure PL/SQL de alerta
            String sql = "BEGIN " +
                    "pkg_insercao_dados.inserir_alerta(?, ?, ?, ?, ?); " +
                    "END;";

            // Executar a chamada à procedure
            int rowsAffected = jdbcTemplate.update(sql,
                    alertaDTO.getIdalerta(),
                    alertaDTO.getDescricao(),
                    alertaDTO.getSeveridade(),
                    dataAlertaSql,
                    alertaDTO.getSensorId()
            );

            if (rowsAffected > 0) {
                return "Alerta criado com sucesso via PROCEDURE!";
            } else {
                throw new RuntimeException("Nenhuma linha foi inserida.");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Já existe um alerta com este ID")) {
                throw new RuntimeException("Erro: Já existe um alerta com este ID.");
            } else if (errorMessage.contains("Erro de tipo de dados ao inserir alerta")) {
                throw new RuntimeException("Erro: Verifique os tipos de dados.");
            } else {
                throw new RuntimeException("Erro desconhecido ao inserir alerta: " + e.getMessage(), e);
            }
        }
    }

    public String inserirDispositivoProcedure(DispositivoDTO dispositivoDTO) {
        try {
            // Formatar a data de instalação
            String dataInstalacaoFormatada = FormatData.formatarData(String.valueOf(dispositivoDTO.getDataInstalacao()));

            SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yy");
            java.util.Date dataInstalacao = sdfEntrada.parse(dataInstalacaoFormatada);
            java.sql.Date dataInstalacaoSql = new java.sql.Date(dataInstalacao.getTime());

            // Preparar SQL para chamar a procedure de inserção de dispositivo
            String sqlDispositivo = "BEGIN " +
                    "pkg_insercao_dados.inserir_dispositivo(?, ?, ?, ?, ?, ?, ?); " +
                    "END;";

            // Executar a chamada para inserir o dispositivo
            jdbcTemplate.update(sqlDispositivo,
                    dispositivoDTO.getIddispositivo(),
                    dispositivoDTO.getNome(),
                    dispositivoDTO.getTipo(),
                    dispositivoDTO.getStatus(),
                    dataInstalacaoSql,
                    dispositivoDTO.getFilialId(),
                    dispositivoDTO.getPotenciaNominal()
            );

            // Iterar pelos IDs dos sensores e associá-los ao dispositivo
            String sqlDispositivoSensor = "BEGIN " +
                    "pkg_insercao_dados.inserir_dispositivo_sensor(?, ?); " +
                    "END;";

            for (Integer sensorId : dispositivoDTO.getSensoresIds()) {
                jdbcTemplate.update(sqlDispositivoSensor,
                        dispositivoDTO.getIddispositivo(),
                        sensorId
                );
            }

            return "Dispositivo e sensores associados com sucesso via PROCEDURE!";

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("DUP_VAL_ON_INDEX")) {
                throw new RuntimeException("Erro: Já existe um dispositivo com este ID ou relacionamento duplicado.");
            } else if (errorMessage.contains("VALUE_ERROR")) {
                throw new RuntimeException("Erro: Verifique os tipos de dados.");
            } else {
                throw new RuntimeException("Erro desconhecido ao inserir dispositivo: " + e.getMessage(), e);
            }
        }
    }

    public String inserirHistoricoProcedure(HistoricoDTO historicoDTO) {
        try {
            // Formatar a data de criação
            String dataCriacaoFormatada = FormatData.formatarData(String.valueOf(historicoDTO.getDataCriacao()));
            SimpleDateFormat sdfEntrada = new SimpleDateFormat("dd/MM/yy");
            java.util.Date dataCriacao = sdfEntrada.parse(dataCriacaoFormatada);
            java.sql.Date dataCriacaoSql = new java.sql.Date(dataCriacao.getTime());

            // Chamar a procedure de inserção do histórico
            String sqlHistorico = "BEGIN " +
                    "pkg_insercao_dados.inserir_historico(?, ?, ?, ?, ?); " +
                    "END;";

            jdbcTemplate.update(sqlHistorico,
                    historicoDTO.getIdhistorico(),
                    dataCriacaoSql,
                    historicoDTO.getIntensidadeCarbono(),
                    historicoDTO.getSensoresIds().get(0),
                    historicoDTO.getRegulacaoEnergiaId()
            );

            // Associar sensores ao histórico na tabela intermediária
            String sqlHistoricoSensor = "BEGIN " +
                    "pkg_insercao_dados.inserir_historico_sensor(?, ?); " +
                    "END;";

            for (Integer sensorId : historicoDTO.getSensoresIds()) {
                jdbcTemplate.update(sqlHistoricoSensor,
                        historicoDTO.getIdhistorico(),
                        sensorId
                );
            }

            return "Histórico e sensores associados com sucesso via PROCEDURE!";

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("DUP_VAL_ON_INDEX")) {
                throw new RuntimeException("Erro: Já existe um histórico com este ID ou relacionamento duplicado.");
            } else if (errorMessage.contains("VALUE_ERROR")) {
                throw new RuntimeException("Erro: Verifique os tipos de dados.");
            } else {
                throw new RuntimeException("Erro desconhecido ao inserir histórico: " + e.getMessage(), e);
            }
        }
    }


    public String inserirSensorProcedure(SensorDTO sensorDTO) {
        try {
            // Preparar o SQL para chamar a procedure PL/SQL de inserção de sensor
            String sqlSensor = "BEGIN " +
                    "pkg_insercao_dados.inserir_sensor(?, ?, ?, ?, ?, ?); " +
                    "END;";

            // Executar a chamada à procedure para inserir o sensor
            int rowsAffected = jdbcTemplate.update(sqlSensor,
                    sensorDTO.getIdsensor(),
                    sensorDTO.getTipo(),
                    sensorDTO.getDescricao(),
                    sensorDTO.getUnidade(),
                    sensorDTO.getValorAtual(),
                    sensorDTO.getTempoOperacao()
            );

            if (rowsAffected > 0) {
                // Se inserção do sensor for bem-sucedida, associamos o sensor com dispositivos e históricos, se necessário.
                if (sensorDTO.getDispositivosIds() != null && !sensorDTO.getDispositivosIds().isEmpty()) {
                    // Inserir associação com os dispositivos
                    String sqlDispositivoSensor = "BEGIN " +
                            "pkg_insercao_dados.inserir_dispositivo_sensor(?, ?); " +
                            "END;";

                    for (Integer dispositivoId : sensorDTO.getDispositivosIds()) {
                        jdbcTemplate.update(sqlDispositivoSensor,
                                dispositivoId,
                                sensorDTO.getIdsensor()
                        );
                    }
                }

                if (sensorDTO.getHistoricosIds() != null && !sensorDTO.getHistoricosIds().isEmpty()) {
                    // Inserir associação com os históricos
                    String sqlHistoricoSensor = "BEGIN " +
                            "pkg_insercao_dados.inserir_historico_sensor(?, ?); " +
                            "END;";

                    for (Integer historicoId : sensorDTO.getHistoricosIds()) {
                        jdbcTemplate.update(sqlHistoricoSensor,
                                historicoId,
                                sensorDTO.getIdsensor()
                        );
                    }
                }

                return "Sensor inserido com sucesso e associado a dispositivos e históricos via PROCEDURE!";
            } else {
                throw new RuntimeException("Nenhuma linha foi inserida.");
            }

        } catch (Exception e) {
            String errorMessage = e.getMessage();
            if (errorMessage.contains("Já existe um sensor com este ID")) {
                throw new RuntimeException("Erro: Já existe um sensor com este ID.");
            } else if (errorMessage.contains("Erro de tipo de dados ao inserir sensor")) {
                throw new RuntimeException("Erro: Verifique os tipos de dados.");
            } else {
                throw new RuntimeException("Erro desconhecido ao inserir sensor: " + e.getMessage(), e);
            }
        }
    }





}
