package com.taligado.energy.service;

import com.taligado.energy.dto.AlertaDTO;
import com.taligado.energy.exception.ResourceNotFoundException;
import com.taligado.energy.model.Alerta;
import com.taligado.energy.model.Sensor;
import com.taligado.energy.repository.IAlertaRepository;
import com.taligado.energy.repository.ISensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlertaService {

    @Autowired
    private IAlertaRepository alertaRepository;

    @Autowired
    private ISensorRepository sensorRepository;

    // Buscar todos os alertas e retornar como DTOs
    public List<AlertaDTO> getAllAlertas() {
        List<Alerta> alertas = alertaRepository.findAll();
        return alertas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar alerta por ID e retornar como DTO
    public AlertaDTO getAlertaById(Integer id) {
        Optional<Alerta> alerta = alertaRepository.findById(id);
        return alerta.map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com o ID: " + id));
    }

    // Salvar um novo alerta e retornar como DTO
    public AlertaDTO saveAlerta(AlertaDTO alertaDTO) {
        Alerta alerta = mapToEntity(alertaDTO);
        Alerta savedAlerta = alertaRepository.save(alerta);
        return mapToDTO(savedAlerta);
    }

    public AlertaDTO updateAlerta(Integer id, AlertaDTO alertaDTO) {
        // Buscar o alerta existente
        Alerta alertaExistente = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado com o ID: " + id));

        // Atualizar os campos do alerta existente
        alertaExistente.setDescricao(alertaDTO.getDescricao());
        alertaExistente.setSeveridade(alertaDTO.getSeveridade());
        alertaExistente.setDataAlerta(alertaDTO.getDataAlerta());

        // Atualizar o sensor relacionado, se fornecido
        if (alertaDTO.getSensorId() != null) {
            Sensor sensor = sensorRepository.findById(alertaDTO.getSensorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com o ID: " + alertaDTO.getSensorId()));
            alertaExistente.setSensor(sensor);
        } else {
            alertaExistente.setSensor(null);
        }

        // Salvar a entidade atualizada
        Alerta updatedAlerta = alertaRepository.save(alertaExistente);

        return mapToDTO(updatedAlerta);
    }


    // Excluir um alerta
    public void deleteAlerta(Integer id) {
        if (!alertaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alerta não encontrado com o ID: " + id);
        }
        alertaRepository.deleteById(id);
    }

    // Método para converter Alerta para AlertaDTO
    private AlertaDTO mapToDTO(Alerta alerta) {
        AlertaDTO alertaDTO = new AlertaDTO();
        alertaDTO.setIdalerta(alerta.getIdalerta());
        alertaDTO.setDescricao(alerta.getDescricao());
        alertaDTO.setSeveridade(alerta.getSeveridade());
        alertaDTO.setDataAlerta(alerta.getDataAlerta());
        alertaDTO.setSensorId(alerta.getSensor() != null ? alerta.getSensor().getIdsensor() : null);
        return alertaDTO;
    }

    // Método para converter AlertaDTO para Alerta
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
