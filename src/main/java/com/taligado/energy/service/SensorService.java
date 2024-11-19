package com.taligado.energy.service;

import com.taligado.energy.dto.SensorDTO;
import com.taligado.energy.exception.ResourceNotFoundException;
import com.taligado.energy.model.Dispositivo;
import com.taligado.energy.model.Historico;
import com.taligado.energy.model.Sensor;
import com.taligado.energy.repository.IDispositivoRepository;
import com.taligado.energy.repository.IHistoricoRepository;
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

    @Autowired
    private IDispositivoRepository dispositivoRepository;

    @Autowired
    private IHistoricoRepository historicoRepository;


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

    public SensorDTO saveSensor(SensorDTO sensorDTO) {
        Sensor sensor = mapToEntity(sensorDTO);

        Sensor savedSensor = sensorRepository.save(sensor);

        System.out.println("Sensor salvo com ID: " + savedSensor.getIdsensor());

        return mapToDTO(savedSensor);
    }



    // Atualizar um sensor existente e retornar como DTO
    public SensorDTO updateSensor(Integer id, SensorDTO sensorDTO) {
        // Buscar o sensor existente
        Sensor sensorExistente = sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com o ID: " + id));

        // Atualizar apenas os campos necessários
        sensorExistente.setTipo(sensorDTO.getTipo());
        sensorExistente.setDescricao(sensorDTO.getDescricao());
        sensorExistente.setUnidade(sensorDTO.getUnidade());
        sensorExistente.setValorAtual(sensorDTO.getValorAtual());
        sensorExistente.setTempoOperacao(sensorDTO.getTempoOperacao());

        // Salvar o sensor atualizado
        Sensor updatedSensor = sensorRepository.save(sensorExistente);

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

    // Método para converter SensorDTO para Sensor
    private Sensor mapToEntity(SensorDTO sensorDTO) {
        Sensor sensor = new Sensor();
        sensor.setTipo(sensorDTO.getTipo());
        sensor.setDescricao(sensorDTO.getDescricao());
        sensor.setUnidade(sensorDTO.getUnidade());
        sensor.setValorAtual(sensorDTO.getValorAtual());
        sensor.setTempoOperacao(sensorDTO.getTempoOperacao());

        // Associa dispositivos e históricos
        List<Dispositivo> dispositivos = dispositivoRepository.findAllById(sensorDTO.getDispositivosIds());
        List<Historico> historicos = historicoRepository.findAllById(sensorDTO.getHistoricosIds());

        sensor.setDispositivos(dispositivos);
        sensor.setHistoricos(historicos);

        return sensor;
    }

}
