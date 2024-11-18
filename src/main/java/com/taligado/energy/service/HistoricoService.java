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
        Historico historico = mapToEntity(historicoDTO);
        Historico savedHistorico = historicoRepository.save(historico);
        return mapToDTO(savedHistorico);
    }

    // Atualizar um histórico existente
    public HistoricoDTO updateHistorico(Integer id, HistoricoDTO historicoDTO) {
        if (!historicoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Histórico não encontrado com o ID: " + id);
        }
        historicoDTO.setIdhistorico(id);
        Historico historico = mapToEntity(historicoDTO);
        Historico updatedHistorico = historicoRepository.save(historico);
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
        historicoDTO.setRegulacaoEnergiaId(historico.getRegulacaoEnergia().getIdregulacao());

        // Mapeamento dos sensores
        historicoDTO.setSensoresIds(historico.getSensores().stream()
                .map(Sensor::getIdsensor)
                .collect(Collectors.toList()));

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

        // Buscar os sensores com base nos IDs
        List<Sensor> sensores = sensorRepository.findAllById(historicoDTO.getSensoresIds());
        historico.setSensores(sensores);

        return historico;
    }
}
