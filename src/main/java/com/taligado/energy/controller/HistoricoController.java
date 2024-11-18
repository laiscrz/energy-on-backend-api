package com.taligado.energy.controller;

import com.taligado.energy.dto.HistoricoDTO;
import com.taligado.energy.model.Historico;
import com.taligado.energy.service.HistoricoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historicos")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    // Endpoint para buscar todos os históricos
    @GetMapping
    public List<HistoricoDTO> getAllHistoricos() {
        return historicoService.getAllHistoricos();
    }

    // Endpoint para buscar um histórico por ID
    @GetMapping("/{id}")
    public ResponseEntity<HistoricoDTO> getHistoricoById(@PathVariable Integer id) {
        Optional<HistoricoDTO> historico = historicoService.getHistoricoById(id);
        return historico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar um novo histórico
    @PostMapping
    public ResponseEntity<HistoricoDTO> createHistorico(@RequestBody Historico historico) {
        HistoricoDTO savedHistorico = historicoService.saveHistorico(historico);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHistorico);
    }

    // Endpoint para atualizar um histórico existente
    @PutMapping("/{id}")
    public ResponseEntity<HistoricoDTO> updateHistorico(@PathVariable Integer id, @RequestBody Historico historicoDetails) {
        HistoricoDTO updatedHistorico = historicoService.updateHistorico(id, historicoDetails);
        if (updatedHistorico != null) {
            return ResponseEntity.ok(updatedHistorico);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir um histórico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHistorico(@PathVariable Integer id) {
        historicoService.deleteHistorico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
