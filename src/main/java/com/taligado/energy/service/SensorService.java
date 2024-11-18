package com.taligado.energy.service;

import com.taligado.energy.dto.SensorDTO;
import com.taligado.energy.model.Dispositivo;
import com.taligado.energy.model.Historico;
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


    // Buscar todos os sensores e convertê-los para DTOs
    public List<SensorDTO> getAllSensores() {
        List<Sensor> sensores = sensorRepository.findAll();
        return sensores.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Buscar sensor por ID e convertê-lo para DTO
    public SensorDTO getSensorById(Integer id) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        return sensor.map(this::convertToDTO).orElse(null);
    }

    // Salvar um novo sensor
    public SensorDTO saveSensor(SensorDTO sensorDTO) {
        Sensor sensor = convertToEntity(sensorDTO);
        Sensor savedSensor = sensorRepository.save(sensor);
        return convertToDTO(savedSensor);
    }

    // Atualizar um sensor existente
    public SensorDTO updateSensor(Integer id, SensorDTO sensorDTO) {
        if (sensorRepository.existsById(id)) {
            sensorDTO.setIdsensor(id);
            Sensor sensor = convertToEntity(sensorDTO);
            Sensor updatedSensor = sensorRepository.save(sensor);
            return convertToDTO(updatedSensor);
        }
        return null;
    }

    // Excluir um sensor
    public void deleteSensor(Integer id) {
        sensorRepository.deleteById(id);
    }

    // Converter Sensor para SensorDTO
    private SensorDTO convertToDTO(Sensor sensor) {
        SensorDTO sensorDTO = new SensorDTO();
        sensorDTO.setIdsensor(sensor.getIdsensor());
        sensorDTO.setTipo(sensor.getTipo());
        sensorDTO.setDescricao(sensor.getDescricao());
        sensorDTO.setUnidade(sensor.getUnidade());
        sensorDTO.setValorAtual(sensor.getValorAtual());
        sensorDTO.setTempoOperacao(sensor.getTempoOperacao());

        List<Integer> dispositivosIds = sensor.getDispositivos().stream()
                .map(Dispositivo::getIddispositivo)
                .collect(Collectors.toList());
        sensorDTO.setDispositivosIds(dispositivosIds);

        List<Integer> historicosIds = sensor.getHistoricos().stream()
                .map(Historico::getIdhistorico)
                .collect(Collectors.toList());
        sensorDTO.setHistoricosIds(historicosIds);



        return sensorDTO;
    }


    // Converter SensorDTO para Sensor
    private Sensor convertToEntity(SensorDTO sensorDTO) {
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
