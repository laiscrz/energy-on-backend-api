package com.taligado.energy.controller;

import com.taligado.energy.model.Filial;
import com.taligado.energy.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/filiais")
public class FilialController {

    @Autowired
    private FilialService filialService;

    // Endpoint para buscar todas as filiais
    @GetMapping
    public List<Filial> getAllFiliais() {
        return filialService.getAllFiliais();
    }

    // Endpoint para buscar uma filial por ID
    @GetMapping("/{id}")
    public ResponseEntity<Filial> getFilialById(@PathVariable Integer id) {
        Optional<Filial> filial = filialService.getFilialById(id);
        return filial.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar uma nova filial
    @PostMapping
    public ResponseEntity<Filial> createFilial(@RequestBody Filial filial) {
        Filial savedFilial = filialService.saveFilial(filial);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFilial);
    }

    // Endpoint para atualizar uma filial existente
    @PutMapping("/{id}")
    public ResponseEntity<Filial> updateFilial(@PathVariable Integer id, @RequestBody Filial filialDetails) {
        Filial updatedFilial = filialService.updateFilial(id, filialDetails);
        if (updatedFilial != null) {
            return ResponseEntity.ok(updatedFilial);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir uma filial
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilial(@PathVariable Integer id) {
        filialService.deleteFilial(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

