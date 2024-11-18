package com.taligado.energy.service;

import com.taligado.energy.dto.AlertaDTO;
import com.taligado.energy.model.Alerta;
import com.taligado.energy.model.Sensor;
import com.taligado.energy.repository.IAlertaRepository;
import com.taligado.energy.repository.ISensorRepository; // Adicionei o repositório para buscar o Sensor
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlertaService {

    @Autowired
    private IAlertaRepository alertaRepository;

    @Autowired
    private ISensorRepository sensorRepository; // Repositório para buscar o Sensor

    // Buscar todos os alertas
    public List<AlertaDTO> getAllAlertas() {
        List<Alerta> alertas = alertaRepository.findAll();
        return alertas.stream().map(this::mapToDTO).toList();
    }

    // Buscar alerta por ID
    public AlertaDTO getAlertaById(Integer id) {
        Optional<Alerta> alerta = alertaRepository.findById(id);
        return alerta.map(this::mapToDTO).orElse(null);
    }

    // Salvar um novo alerta
    public AlertaDTO saveAlerta(AlertaDTO alertaDTO) {
        Alerta alerta = mapToEntity(alertaDTO);
        Alerta savedAlerta = alertaRepository.save(alerta);
        return mapToDTO(savedAlerta);
    }

    // Atualizar um alerta existente
    public AlertaDTO updateAlerta(Integer id, AlertaDTO alertaDetails) {
        if (alertaRepository.existsById(id)) {
            alertaDetails.setIdalerta(id);
            Alerta alerta = mapToEntity(alertaDetails);
            Alerta updatedAlerta = alertaRepository.save(alerta);
            return mapToDTO(updatedAlerta);
        }
        return null;
    }

    // Excluir um alerta
    public void deleteAlerta(Integer id) {
        alertaRepository.deleteById(id);
    }

    // Método de mapeamento de entidade para DTO
    private AlertaDTO mapToDTO(Alerta alerta) {
        AlertaDTO alertaDTO = new AlertaDTO();
        alertaDTO.setIdalerta(alerta.getIdalerta());
        alertaDTO.setDescricao(alerta.getDescricao());
        alertaDTO.setSeveridade(alerta.getSeveridade());
        alertaDTO.setDataAlerta(alerta.getDataAlerta());
        alertaDTO.setSensorId(alerta.getSensor() != null ? alerta.getSensor().getIdsensor() : null); // Mapear apenas o ID do sensor
        return alertaDTO;
    }

    // Método de mapeamento de DTO para entidade
    private Alerta mapToEntity(AlertaDTO alertaDTO) {
        Alerta alerta = new Alerta();
        alerta.setDescricao(alertaDTO.getDescricao());
        alerta.setSeveridade(alertaDTO.getSeveridade());
        alerta.setDataAlerta(alertaDTO.getDataAlerta());

        // Buscar o Sensor a partir do ID
        if (alertaDTO.getSensorId() != null) {
            Optional<Sensor> sensor = sensorRepository.findById(alertaDTO.getSensorId());
            sensor.ifPresent(alerta::setSensor);
        }

        return alerta;
    }
}
