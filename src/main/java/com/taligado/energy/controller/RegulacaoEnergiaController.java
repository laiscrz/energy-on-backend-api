package com.taligado.energy.controller;

import com.taligado.energy.model.RegulacaoEnergia;
import com.taligado.energy.service.RegulacaoEnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/regulacoes-energia")
public class RegulacaoEnergiaController {

    @Autowired
    private RegulacaoEnergiaService regulacaoEnergiaService;

    // Endpoint para buscar todas as regulações de energia
    @GetMapping
    public List<RegulacaoEnergia> getAllRegulacoesEnergia() {
        return regulacaoEnergiaService.getAllRegulacoes();
    }

    // Endpoint para buscar uma regulação de energia por ID
    @GetMapping("/{id}")
    public ResponseEntity<RegulacaoEnergia> getRegulacaoEnergiaById(@PathVariable Integer id) {
        Optional<RegulacaoEnergia> regulacaoEnergia = regulacaoEnergiaService.getRegulacaoById(id);
        return regulacaoEnergia.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar uma nova regulação de energia
    @PostMapping
    public ResponseEntity<RegulacaoEnergia> createRegulacaoEnergia(@RequestBody RegulacaoEnergia regulacaoEnergia) {
        RegulacaoEnergia savedRegulacaoEnergia = regulacaoEnergiaService.saveRegulacao(regulacaoEnergia);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegulacaoEnergia);
    }

    // Endpoint para atualizar uma regulação de energia existente
    @PutMapping("/{id}")
    public ResponseEntity<RegulacaoEnergia> updateRegulacaoEnergia(@PathVariable Integer id, @RequestBody RegulacaoEnergia regulacaoEnergiaDetails) {
        RegulacaoEnergia updatedRegulacaoEnergia = regulacaoEnergiaService.updateRegulacao(id, regulacaoEnergiaDetails);
        if (updatedRegulacaoEnergia != null) {
            return ResponseEntity.ok(updatedRegulacaoEnergia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir uma regulação de energia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegulacaoEnergia(@PathVariable Integer id) {
        regulacaoEnergiaService.deleteRegulacao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

