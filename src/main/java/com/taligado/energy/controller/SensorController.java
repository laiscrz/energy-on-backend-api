package com.taligado.energy.controller;

import com.taligado.energy.model.Sensor;
import com.taligado.energy.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sensores")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // Endpoint para buscar todos os sensores
    @GetMapping
    public List<Sensor> getAllSensores() {
        return sensorService.getAllSensores();
    }

    // Endpoint para buscar um sensor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Sensor> getSensorById(@PathVariable Integer id) {
        Optional<Sensor> sensor = sensorService.getSensorById(id);
        return sensor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar um novo sensor
    @PostMapping
    public ResponseEntity<Sensor> createSensor(@RequestBody Sensor sensor) {
        Sensor savedSensor = sensorService.saveSensor(sensor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSensor);
    }

    // Endpoint para atualizar um sensor existente
    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Integer id, @RequestBody Sensor sensorDetails) {
        Sensor updatedSensor = sensorService.updateSensor(id, sensorDetails);
        if (updatedSensor != null) {
            return ResponseEntity.ok(updatedSensor);
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

