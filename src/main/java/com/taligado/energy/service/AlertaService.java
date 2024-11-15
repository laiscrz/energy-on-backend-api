package com.taligado.energy.service;

import com.taligado.energy.model.Alerta;
import com.taligado.energy.repository.IAlertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private IAlertaRepository alertaRepository;

    // Buscar todos os alertas
    public List<Alerta> getAllAlertas() {
        return alertaRepository.findAll();
    }

    // Buscar alerta por ID
    public Optional<Alerta> getAlertaById(Integer id) {
        return alertaRepository.findById(id);
    }

    // Salvar um novo alerta
    public Alerta saveAlerta(Alerta alerta) {
        return alertaRepository.save(alerta);
    }

    // Atualizar um alerta existente
    public Alerta updateAlerta(Integer id, Alerta alertaDetails) {
        if (alertaRepository.existsById(id)) {
            alertaDetails.setIdalerta(id);
            return alertaRepository.save(alertaDetails);
        }
        return null;
    }

    // Excluir um alerta
    public void deleteAlerta(Integer id) {
        alertaRepository.deleteById(id);
    }
}

