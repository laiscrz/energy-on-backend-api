package com.taligado.energy.controller;

import com.taligado.energy.dto.RegulacaoEnergiaDTO;
import com.taligado.energy.service.RegulacaoEnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regulacoes-energia")
public class RegulacaoEnergiaController {

    @Autowired
    private RegulacaoEnergiaService regulacaoEnergiaService;

    // Endpoint para buscar todas as regulações de energia
    @GetMapping
    public List<RegulacaoEnergiaDTO> getAllRegulacoesEnergia() {
        return regulacaoEnergiaService.getAllRegulacoes();
    }

    // Endpoint para buscar uma regulação de energia por ID
    @GetMapping("/{id}")
    public ResponseEntity<RegulacaoEnergiaDTO> getRegulacaoEnergiaById(@PathVariable Integer id) {
        RegulacaoEnergiaDTO regulacaoEnergiaDTO = regulacaoEnergiaService.getRegulacaoById(id);
        return regulacaoEnergiaDTO != null ? ResponseEntity.ok(regulacaoEnergiaDTO)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar uma nova regulação de energia
    @PostMapping
    public ResponseEntity<RegulacaoEnergiaDTO> createRegulacaoEnergia(@RequestBody RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        RegulacaoEnergiaDTO savedRegulacaoEnergiaDTO = regulacaoEnergiaService.saveRegulacao(regulacaoEnergiaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegulacaoEnergiaDTO);
    }

    // Endpoint para atualizar uma regulação de energia existente
    @PutMapping("/{id}")
    public ResponseEntity<RegulacaoEnergiaDTO> updateRegulacaoEnergia(@PathVariable Integer id, @RequestBody RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        RegulacaoEnergiaDTO updatedRegulacaoEnergiaDTO = regulacaoEnergiaService.updateRegulacao(id, regulacaoEnergiaDTO);
        if (updatedRegulacaoEnergiaDTO != null) {
            return ResponseEntity.ok(updatedRegulacaoEnergiaDTO);
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
