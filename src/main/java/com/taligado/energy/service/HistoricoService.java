package com.taligado.energy.service;

import com.taligado.energy.model.Historico;
import com.taligado.energy.repository.IHistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HistoricoService {

    @Autowired
    private IHistoricoRepository historicoRepository;

    // Buscar todos os históricos
    public List<Historico> getAllHistoricos() {
        return historicoRepository.findAll();
    }

    // Buscar histórico por ID
    public Optional<Historico> getHistoricoById(Integer id) {
        return historicoRepository.findById(id);
    }

    // Salvar um novo histórico
    public Historico saveHistorico(Historico historico) {
        return historicoRepository.save(historico);
    }

    // Atualizar um histórico existente
    public Historico updateHistorico(Integer id, Historico historicoDetails) {
        if (historicoRepository.existsById(id)) {
            historicoDetails.setIdhistorico(id);
            return historicoRepository.save(historicoDetails);
        }
        return null;
    }

    // Excluir um histórico
    public void deleteHistorico(Integer id) {
        historicoRepository.deleteById(id);
    }
}

