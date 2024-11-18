package com.taligado.energy.controller;

import com.taligado.energy.dto.FilialDTO;
import com.taligado.energy.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filiais")
public class FilialController {

    @Autowired
    private FilialService filialService;

    // Endpoint para buscar todas as filiais
    @GetMapping
    public List<FilialDTO> getAllFiliais() {
        return filialService.getAllFiliais();
    }

    // Endpoint para buscar uma filial por ID
    @GetMapping("/{id}")
    public ResponseEntity<FilialDTO> getFilialById(@PathVariable Integer id) {
        FilialDTO filial = filialService.getFilialById(id);
        return filial != null ? ResponseEntity.ok(filial) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar uma nova filial
    @PostMapping
    public ResponseEntity<FilialDTO> createFilial(@RequestBody FilialDTO filialDTO) {
        FilialDTO savedFilial = filialService.saveFilial(filialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFilial);
    }

    // Endpoint para atualizar uma filial existente
    @PutMapping("/{id}")
    public ResponseEntity<FilialDTO> updateFilial(@PathVariable Integer id, @RequestBody FilialDTO filialDTO) {
        FilialDTO updatedFilial = filialService.updateFilial(id, filialDTO);
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
