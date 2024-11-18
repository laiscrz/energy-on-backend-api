package com.taligado.energy.service;

import com.taligado.energy.dto.HistoricoDTO;
import com.taligado.energy.model.Historico;
import com.taligado.energy.model.Sensor;
import com.taligado.energy.repository.IHistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoricoService {

    @Autowired
    private IHistoricoRepository historicoRepository;

    // Buscar todos os históricos
    public List<HistoricoDTO> getAllHistoricos() {
        List<Historico> historicos = historicoRepository.findAll();
        return historicos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Buscar histórico por ID
    public Optional<HistoricoDTO> getHistoricoById(Integer id) {
        Optional<Historico> historico = historicoRepository.findById(id);
        return historico.map(this::convertToDTO);
    }

    // Salvar um novo histórico
    public HistoricoDTO saveHistorico(Historico historico) {
        Historico savedHistorico = historicoRepository.save(historico);
        return convertToDTO(savedHistorico);
    }

    // Atualizar um histórico existente
    public HistoricoDTO updateHistorico(Integer id, Historico historicoDetails) {
        if (historicoRepository.existsById(id)) {
            historicoDetails.setIdhistorico(id);
            Historico updatedHistorico = historicoRepository.save(historicoDetails);
            return convertToDTO(updatedHistorico);
        }
        return null;
    }

    // Excluir um histórico
    public void deleteHistorico(Integer id) {
        historicoRepository.deleteById(id);
    }

    // Converter Historico para HistoricoDTO
    private HistoricoDTO convertToDTO(Historico historico) {
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
}
