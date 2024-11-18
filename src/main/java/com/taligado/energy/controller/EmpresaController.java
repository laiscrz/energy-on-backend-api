package com.taligado.energy.controller;

import com.taligado.energy.dto.EmpresaDTO;
import com.taligado.energy.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Endpoint para buscar todas as empresas
    @GetMapping
    public List<EmpresaDTO> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }

    // Endpoint para buscar uma empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresaById(@PathVariable Integer id) {
        EmpresaDTO empresa = empresaService.getEmpresaById(id);
        return empresa != null ? ResponseEntity.ok(empresa) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar uma nova empresa
    @PostMapping
    public ResponseEntity<EmpresaDTO> createEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO savedEmpresa = empresaService.saveEmpresa(empresaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpresa);
    }

    // Endpoint para atualizar uma empresa existente
    @PutMapping("/{id}")
    public ResponseEntity<EmpresaDTO> updateEmpresa(@PathVariable Integer id, @RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO updatedEmpresa = empresaService.updateEmpresa(id, empresaDTO);
        if (updatedEmpresa != null) {
            return ResponseEntity.ok(updatedEmpresa);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir uma empresa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Integer id) {
        empresaService.deleteEmpresa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
