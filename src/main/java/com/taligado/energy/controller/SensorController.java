package com.taligado.energy.controller;

import com.taligado.energy.dto.SensorDTO;
import com.taligado.energy.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // Endpoint para buscar todos os sensores
    @GetMapping
    public List<SensorDTO> getAllSensores() {
        return sensorService.getAllSensores();
    }

    // Endpoint para buscar um sensor por ID
    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable Integer id) {
        SensorDTO sensorDTO = sensorService.getSensorById(id);
        return sensorDTO != null ? ResponseEntity.ok(sensorDTO)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo sensor
    @PostMapping
    public ResponseEntity<SensorDTO> createSensor(@RequestBody SensorDTO sensorDTO) {
        SensorDTO savedSensorDTO = sensorService.saveSensor(sensorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSensorDTO);
    }

    // Endpoint para atualizar um sensor existente
    @PutMapping("/{id}")
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable Integer id, @RequestBody SensorDTO sensorDTO) {
        SensorDTO updatedSensorDTO = sensorService.updateSensor(id, sensorDTO);
        if (updatedSensorDTO != null) {
            return ResponseEntity.ok(updatedSensorDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir um sensor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSensor(@PathVariable Integer id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
