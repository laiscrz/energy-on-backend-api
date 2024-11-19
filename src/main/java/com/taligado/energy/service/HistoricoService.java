package com.taligado.energy.service;

import com.taligado.energy.dto.HistoricoDTO;
import com.taligado.energy.exception.ResourceNotFoundException;
import com.taligado.energy.model.Historico;
import com.taligado.energy.model.Sensor;
import com.taligado.energy.model.RegulacaoEnergia;
import com.taligado.energy.repository.IHistoricoRepository;
import com.taligado.energy.repository.IRegulacaoEnergiaRepository;
import com.taligado.energy.repository.ISensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoricoService {

    @Autowired
    private IHistoricoRepository historicoRepository;

    @Autowired
    private IRegulacaoEnergiaRepository regulacaoEnergiaRepository;

    @Autowired
    private ISensorRepository sensorRepository;

    @Autowired
    private ProceduresService proceduresService;

    // Buscar todos os históricos
    public List<HistoricoDTO> getAllHistoricos() {
        List<Historico> historicos = historicoRepository.findAll();
        return historicos.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Buscar histórico por ID
    public HistoricoDTO getHistoricoById(Integer id) {
        Optional<Historico> historico = historicoRepository.findById(id);
        return historico.map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Histórico não encontrado com o ID: " + id));
    }

    // Salvar um novo histórico
    public HistoricoDTO saveHistorico(HistoricoDTO historicoDTO) {
        try {
            String resultadoProcedure = proceduresService.inserirHistoricoProcedure(historicoDTO);

            if ("Histórico e sensores associados com sucesso via PROCEDURE!".equals(resultadoProcedure)) {
                return historicoDTO;
            } else {
                throw new RuntimeException("Erro ao inserir historico via procedure.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar historico: " + e.getMessage(), e);
        }
    }

    // Atualizar um histórico existente
    public HistoricoDTO updateHistorico(Integer id, HistoricoDTO historicoDTO) {
        // Buscar o histórico existente
        Historico historicoExistente = historicoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Histórico não encontrado com o ID: " + id));

        // Atualizar apenas os campos necessários
        historicoExistente.setDataCriacao(historicoDTO.getDataCriacao());
        historicoExistente.setValorConsumoKwh(historicoDTO.getValorConsumoKwh());
        historicoExistente.setIntensidadeCarbono(historicoDTO.getIntensidadeCarbono());
        historicoExistente.setCustoEnergiaEstimado(historicoDTO.getCustoEnergiaEstimado());

        // Atualizar a regulacaoEnergia caso o ID tenha sido fornecido
        if (historicoDTO.getRegulacaoEnergiaId() != null) {
            Optional<RegulacaoEnergia> regulacaoEnergia = regulacaoEnergiaRepository.findById(historicoDTO.getRegulacaoEnergiaId());
            regulacaoEnergia.ifPresent(historicoExistente::setRegulacaoEnergia);
        }

        // Atualizar os sensores caso haja novos IDs fornecidos
        if (historicoDTO.getSensoresIds() != null) {
            List<Sensor> sensores = sensorRepository.findAllById(historicoDTO.getSensoresIds());
            historicoExistente.setSensores(sensores);
        }

        // Salvar o histórico atualizado
        Historico updatedHistorico = historicoRepository.save(historicoExistente);
        return mapToDTO(updatedHistorico);
    }


    // Excluir um histórico
    public void deleteHistorico(Integer id) {
        if (!historicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Histórico não encontrado com o ID: " + id);
        }
        historicoRepository.deleteById(id);
    }

    // Converter Historico para HistoricoDTO
    private HistoricoDTO mapToDTO(Historico historico) {
        HistoricoDTO historicoDTO = new HistoricoDTO();
        historicoDTO.setIdhistorico(historico.getIdhistorico());
        historicoDTO.setDataCriacao(historico.getDataCriacao());
        historicoDTO.setValorConsumoKwh(historico.getValorConsumoKwh());
        historicoDTO.setIntensidadeCarbono(historico.getIntensidadeCarbono());
        historicoDTO.setCustoEnergiaEstimado(historico.getCustoEnergiaEstimado());

        // Verificar se existe RegulacaoEnergia antes de tentar acessar o ID
        if (historico.getRegulacaoEnergia() != null) {
            historicoDTO.setRegulacaoEnergiaId(historico.getRegulacaoEnergia().getIdregulacao());
        }

        // Mapeamento dos sensores
        if (historico.getSensores() != null) {
            historicoDTO.setSensoresIds(historico.getSensores().stream()
                    .map(Sensor::getIdsensor)
                    .collect(Collectors.toList()));
        }

        return historicoDTO;
    }


    // Converter HistoricoDTO para Historico (entidade)
    private Historico mapToEntity(HistoricoDTO historicoDTO) {
        Historico historico = new Historico();
        historico.setDataCriacao(historicoDTO.getDataCriacao());
        historico.setValorConsumoKwh(historicoDTO.getValorConsumoKwh());
        historico.setIntensidadeCarbono(historicoDTO.getIntensidadeCarbono());
        historico.setCustoEnergiaEstimado(historicoDTO.getCustoEnergiaEstimado());

        // Buscar a entidade RegulacaoEnergia com base no ID
        Optional<RegulacaoEnergia> regulacaoEnergia = regulacaoEnergiaRepository.findById(historicoDTO.getRegulacaoEnergiaId());
        regulacaoEnergia.ifPresent(historico::setRegulacaoEnergia);

        List<Sensor> sensores = sensorRepository.findAllById(historicoDTO.getSensoresIds());
        historico.setSensores(sensores);

        return historico;
    }
}
