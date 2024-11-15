package com.taligado.energy.service;

import com.taligado.energy.model.Sensor;
import com.taligado.energy.repository.ISensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private ISensorRepository sensorRepository;

    // Buscar todos os sensores
    public List<Sensor> getAllSensores() {
        return sensorRepository.findAll();
    }

    // Buscar sensor por ID
    public Optional<Sensor> getSensorById(Integer id) {
        return sensorRepository.findById(id);
    }

    // Salvar um novo sensor
    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    // Atualizar um sensor existente
    public Sensor updateSensor(Integer id, Sensor sensorDetails) {
        if (sensorRepository.existsById(id)) {
            sensorDetails.setIdsensor(id);
            return sensorRepository.save(sensorDetails);
        }
        return null;
    }

    // Excluir um sensor
    public void deleteSensor(Integer id) {
        sensorRepository.deleteById(id);
    }
}

