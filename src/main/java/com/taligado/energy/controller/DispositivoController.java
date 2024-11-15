package com.taligado.energy.controller;

import com.taligado.energy.model.Dispositivo;
import com.taligado.energy.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    // Endpoint para buscar todos os dispositivos
    @GetMapping
    public List<Dispositivo> getAllDispositivos() {
        return dispositivoService.getAllDispositivos();
    }

    // Endpoint para buscar um dispositivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Dispositivo> getDispositivoById(@PathVariable Integer id) {
        Optional<Dispositivo> dispositivo = dispositivoService.getDispositivoById(id);
        return dispositivo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar um novo dispositivo
    @PostMapping
    public ResponseEntity<Dispositivo> createDispositivo(@RequestBody Dispositivo dispositivo) {
        Dispositivo savedDispositivo = dispositivoService.saveDispositivo(dispositivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDispositivo);
    }

    // Endpoint para atualizar um dispositivo existente
    @PutMapping("/{id}")
    public ResponseEntity<Dispositivo> updateDispositivo(@PathVariable Integer id, @RequestBody Dispositivo dispositivoDetails) {
        Dispositivo updatedDispositivo = dispositivoService.updateDispositivo(id, dispositivoDetails);
        if (updatedDispositivo != null) {
            return ResponseEntity.ok(updatedDispositivo);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir um dispositivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispositivo(@PathVariable Integer id) {
        dispositivoService.deleteDispositivo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

