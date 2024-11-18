package com.taligado.energy.controller;

import com.taligado.energy.dto.AlertaDTO;
import com.taligado.energy.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    // Endpoint para buscar todos os alertas
    @GetMapping
    public List<AlertaDTO> getAllAlertas() {
        return alertaService.getAllAlertas();
    }

    // Endpoint para buscar um alerta por ID
    @GetMapping("/{id}")
    public ResponseEntity<AlertaDTO> getAlertaById(@PathVariable Integer id) {
        AlertaDTO alerta = alertaService.getAlertaById(id);
        return alerta != null ? ResponseEntity.ok(alerta) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo alerta
    @PostMapping
    public ResponseEntity<AlertaDTO> createAlerta(@RequestBody AlertaDTO alertaDTO) {
        AlertaDTO savedAlerta = alertaService.saveAlerta(alertaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAlerta);
    }

    // Endpoint para atualizar um alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<AlertaDTO> updateAlerta(@PathVariable Integer id, @RequestBody AlertaDTO alertaDetails) {
        AlertaDTO updatedAlerta = alertaService.updateAlerta(id, alertaDetails);
        return updatedAlerta != null ? ResponseEntity.ok(updatedAlerta) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir um alerta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Integer id) {
        alertaService.deleteAlerta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
