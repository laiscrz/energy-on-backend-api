package com.taligado.energy.controller;

import com.taligado.energy.model.Endereco;
import com.taligado.energy.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Endpoint para buscar todos os endereços
    @GetMapping
    public List<Endereco> getAllEnderecos() {
        return enderecoService.getAllEnderecos();
    }

    // Endpoint para buscar um endereço por ID
    @GetMapping("/{id}")
    public ResponseEntity<Endereco> getEnderecoById(@PathVariable Integer id) {
        Optional<Endereco> endereco = enderecoService.getEnderecoById(id);
        return endereco.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar um novo endereço
    @PostMapping
    public ResponseEntity<Endereco> createEndereco(@RequestBody Endereco endereco) {
        Endereco savedEndereco = enderecoService.saveEndereco(endereco);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEndereco);
    }

    // Endpoint para atualizar um endereço existente
    @PutMapping("/{id}")
    public ResponseEntity<Endereco> updateEndereco(@PathVariable Integer id, @RequestBody Endereco enderecoDetails) {
        Endereco updatedEndereco = enderecoService.updateEndereco(id, enderecoDetails);
        if (updatedEndereco != null) {
            return ResponseEntity.ok(updatedEndereco);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Endpoint para excluir um endereço
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEndereco(@PathVariable Integer id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

