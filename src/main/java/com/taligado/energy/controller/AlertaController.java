package com.taligado.energy.controller;

import com.taligado.energy.model.Alerta;
import com.taligado.energy.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    // Endpoint para buscar todos os alertas
    @GetMapping
    public List<Alerta> getAllAlertas() {
        return alertaService.getAllAlertas();
    }

    // Endpoint para buscar um alerta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Alerta> getAlertaById(@PathVariable Integer id) {
        Optional<Alerta> alerta = alertaService.getAlertaById(id);
        return alerta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar um novo alerta
    @PostMapping
    public ResponseEntity<Alerta> createAlerta(@RequestBody Alerta alerta) {
        Alerta savedAlerta = alertaService.saveAlerta(alerta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAlerta);
    }

    // Endpoint para atualizar um alerta existente
    @PutMapping("/{id}")
    public ResponseEntity<Alerta> updateAlerta(@PathVariable Integer id, @RequestBody Alerta alertaDetails) {
        Alerta updatedAlerta = alertaService.updateAlerta(id, alertaDetails);
        if (updatedAlerta != null) {
            return ResponseEntity.ok(updatedAlerta);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir um alerta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlerta(@PathVariable Integer id) {
        alertaService.deleteAlerta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

