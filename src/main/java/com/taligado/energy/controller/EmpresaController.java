package com.taligado.energy.controller;

import com.taligado.energy.model.Empresa;
import com.taligado.energy.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Endpoint para buscar todas as empresas
    @GetMapping
    public List<Empresa> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }

    // Endpoint para buscar uma empresa por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getEmpresaById(@PathVariable Integer id) {
        Optional<Empresa> empresa = empresaService.getEmpresaById(id);
        return empresa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar uma nova empresa
    @PostMapping
    public ResponseEntity<Empresa> createEmpresa(@RequestBody Empresa empresa) {
        Empresa savedEmpresa = empresaService.saveEmpresa(empresa);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpresa);
    }

    // Endpoint para atualizar uma empresa existente
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> updateEmpresa(@PathVariable Integer id, @RequestBody Empresa empresaDetails) {
        Empresa updatedEmpresa = empresaService.updateEmpresa(id, empresaDetails);
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

