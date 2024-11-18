package com.taligado.energy.controller;

import com.taligado.energy.dto.DispositivoDTO;
import com.taligado.energy.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    // Endpoint para buscar todos os dispositivos
    @GetMapping
    public List<DispositivoDTO> getAllDispositivos() {
        return dispositivoService.getAllDispositivos();
    }

    // Endpoint para buscar um dispositivo por ID
    @GetMapping("/{id}")
    public ResponseEntity<DispositivoDTO> getDispositivoById(@PathVariable Integer id) {
        DispositivoDTO dispositivoDTO = dispositivoService.getDispositivoById(id);
        return dispositivoDTO != null ? ResponseEntity.ok(dispositivoDTO) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo dispositivo
    @PostMapping
    public ResponseEntity<DispositivoDTO> createDispositivo(@RequestBody DispositivoDTO dispositivoDTO) {
        DispositivoDTO savedDispositivoDTO = dispositivoService.saveDispositivo(dispositivoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDispositivoDTO);
    }

    // Endpoint para atualizar um dispositivo existente
    @PutMapping("/{id}")
    public ResponseEntity<DispositivoDTO> updateDispositivo(@PathVariable Integer id, @RequestBody DispositivoDTO dispositivoDTO) {
        DispositivoDTO updatedDispositivoDTO = dispositivoService.updateDispositivo(id, dispositivoDTO);
        return updatedDispositivoDTO != null ? ResponseEntity.ok(updatedDispositivoDTO) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir um dispositivo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispositivo(@PathVariable Integer id) {
        dispositivoService.deleteDispositivo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
