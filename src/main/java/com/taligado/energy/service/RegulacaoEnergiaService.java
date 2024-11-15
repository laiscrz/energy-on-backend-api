package com.taligado.energy.service;

import com.taligado.energy.model.RegulacaoEnergia;
import com.taligado.energy.repository.IRegulacaoEnergiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegulacaoEnergiaService {

    @Autowired
    private IRegulacaoEnergiaRepository regulacaoEnergiaRepository;

    // Buscar todas as regulações de energia
    public List<RegulacaoEnergia> getAllRegulacoes() {
        return regulacaoEnergiaRepository.findAll();
    }

    // Buscar regulação de energia por ID
    public Optional<RegulacaoEnergia> getRegulacaoById(Integer id) {
        return regulacaoEnergiaRepository.findById(id);
    }

    // Salvar uma nova regulação de energia
    public RegulacaoEnergia saveRegulacao(RegulacaoEnergia regulacaoEnergia) {
        return regulacaoEnergiaRepository.save(regulacaoEnergia);
    }

    // Atualizar uma regulação de energia existente
    public RegulacaoEnergia updateRegulacao(Integer id, RegulacaoEnergia regulacaoEnergiaDetails) {
        if (regulacaoEnergiaRepository.existsById(id)) {
            regulacaoEnergiaDetails.setIdregulacao(id);
            return regulacaoEnergiaRepository.save(regulacaoEnergiaDetails);
        }
        return null;
    }

    // Excluir uma regulação de energia
    public void deleteRegulacao(Integer id) {
        regulacaoEnergiaRepository.deleteById(id);
    }
}

