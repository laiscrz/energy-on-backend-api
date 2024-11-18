package com.taligado.energy.service;

import com.taligado.energy.dto.RegulacaoEnergiaDTO;
import com.taligado.energy.model.RegulacaoEnergia;
import com.taligado.energy.repository.IRegulacaoEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegulacaoEnergiaService {

    @Autowired
    private IRegulacaoEnergiaRepository regulacaoEnergiaRepository;

    // Buscar todas as regulações de energia e retornar como DTOs
    public List<RegulacaoEnergiaDTO> getAllRegulacoes() {
        List<RegulacaoEnergia> regulacoes = regulacaoEnergiaRepository.findAll();
        return regulacoes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar regulação de energia por ID e retornar como DTO
    public RegulacaoEnergiaDTO getRegulacaoById(Integer id) {
        Optional<RegulacaoEnergia> regulacaoEnergia = regulacaoEnergiaRepository.findById(id);
        return regulacaoEnergia.map(this::mapToDTO).orElse(null);
    }

    // Salvar uma nova regulação de energia e retornar como DTO
    public RegulacaoEnergiaDTO saveRegulacao(RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        RegulacaoEnergia regulacaoEnergia = mapToEntity(regulacaoEnergiaDTO);
        RegulacaoEnergia savedRegulacao = regulacaoEnergiaRepository.save(regulacaoEnergia);
        return mapToDTO(savedRegulacao);
    }

    // Atualizar uma regulação de energia existente e retornar como DTO
    public RegulacaoEnergiaDTO updateRegulacao(Integer id, RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        if (regulacaoEnergiaRepository.existsById(id)) {
            regulacaoEnergiaDTO.setIdregulacao(id);
            RegulacaoEnergia regulacaoEnergia = mapToEntity(regulacaoEnergiaDTO);
            RegulacaoEnergia updatedRegulacao = regulacaoEnergiaRepository.save(regulacaoEnergia);
            return mapToDTO(updatedRegulacao);
        }
        return null;
    }

    // Excluir uma regulação de energia
    public void deleteRegulacao(Integer id) {
        regulacaoEnergiaRepository.deleteById(id);
    }

    // Método para converter RegulacaoEnergia para RegulacaoEnergiaDTO
    private RegulacaoEnergiaDTO mapToDTO(RegulacaoEnergia regulacaoEnergia) {
        RegulacaoEnergiaDTO regulacaoEnergiaDTO = new RegulacaoEnergiaDTO();
        regulacaoEnergiaDTO.setIdregulacao(regulacaoEnergia.getIdregulacao());
        regulacaoEnergiaDTO.setTarifaKwh(regulacaoEnergia.getTarifaKwh());
        regulacaoEnergiaDTO.setNomeBandeira(regulacaoEnergia.getNomeBandeira());
        regulacaoEnergiaDTO.setTarifaAdicionalBandeira(regulacaoEnergia.getTarifaAdicionalBandeira());
        regulacaoEnergiaDTO.setDataAtualizacao(regulacaoEnergia.getDataAtualizacao());
        return regulacaoEnergiaDTO;
    }

    // Método para converter RegulacaoEnergiaDTO para RegulacaoEnergia
    private RegulacaoEnergia mapToEntity(RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        RegulacaoEnergia regulacaoEnergia = new RegulacaoEnergia();
        regulacaoEnergia.setTarifaKwh(regulacaoEnergiaDTO.getTarifaKwh());
        regulacaoEnergia.setNomeBandeira(regulacaoEnergiaDTO.getNomeBandeira());
        regulacaoEnergia.setTarifaAdicionalBandeira(regulacaoEnergiaDTO.getTarifaAdicionalBandeira());
        regulacaoEnergia.setDataAtualizacao(regulacaoEnergiaDTO.getDataAtualizacao());
        return regulacaoEnergia;
    }
}
