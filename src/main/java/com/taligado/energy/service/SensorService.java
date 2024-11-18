package com.taligado.energy.service;

import com.taligado.energy.dto.SensorDTO;
import com.taligado.energy.exception.ResourceNotFoundException;
import com.taligado.energy.model.Sensor;
import com.taligado.energy.repository.ISensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SensorService {

    @Autowired
    private ISensorRepository sensorRepository;

    // Buscar todos os sensores e retornar como DTOs
    public List<SensorDTO> getAllSensores() {
        List<Sensor> sensores = sensorRepository.findAll();
        return sensores.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar sensor por ID e retornar como DTO
    public SensorDTO getSensorById(Integer id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com o ID: " + id));
    }

    // Salvar um novo sensor e retornar como DTO
    public SensorDTO saveSensor(SensorDTO sensorDTO) {
        Sensor sensor = mapToEntity(sensorDTO);
        Sensor savedSensor = sensorRepository.save(sensor);
        return mapToDTO(savedSensor);
    }

    // Atualizar um sensor existente e retornar como DTO
    public SensorDTO updateSensor(Integer id, SensorDTO sensorDTO) {
        if (!sensorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sensor não encontrado com o ID: " + id);
        }
        sensorDTO.setIdsensor(id);
        Sensor sensor = mapToEntity(sensorDTO);
        Sensor updatedSensor = sensorRepository.save(sensor);
        return mapToDTO(updatedSensor);
    }

    // Excluir um sensor
    public void deleteSensor(Integer id) {
        if (!sensorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sensor não encontrado com o ID: " + id);
        }
        sensorRepository.deleteById(id);
    }

    // Método para converter Sensor para SensorDTO
    private SensorDTO mapToDTO(Sensor sensor) {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setIdsensor(sensor.getIdsensor());
        sensorDTO.setTipo(sensor.getTipo());
        sensorDTO.setDescricao(sensor.getDescricao());
        sensorDTO.setUnidade(sensor.getUnidade());
        sensorDTO.setValorAtual(sensor.getValorAtual());
        sensorDTO.setTempoOperacao(sensor.getTempoOperacao());

        // Assumindo que o Sensor tem listas de Dispositivos e Históricos que podem ser convertidos para IDs
        sensorDTO.setDispositivosIds(sensor.getDispositivos().stream()
                .map(dispositivo -> dispositivo.getIddispositivo())
                .collect(Collectors.toList()));

        sensorDTO.setHistoricosIds(sensor.getHistoricos().stream()
                .map(historico -> historico.getIdhistorico())
                .collect(Collectors.toList()));

        return sensorDTO;
    }

    // Método para converter SensorDTO para Sensor
    private Sensor mapToEntity(SensorDTO sensorDTO) {
        Sensor sensor = new Sensor();
        sensor.setIdsensor(sensorDTO.getIdsensor());
        sensor.setTipo(sensorDTO.getTipo());
        sensor.setDescricao(sensorDTO.getDescricao());
        sensor.setUnidade(sensorDTO.getUnidade());
        sensor.setValorAtual(sensorDTO.getValorAtual());
        sensor.setTempoOperacao(sensorDTO.getTempoOperacao());
        return sensor;
    }
}
