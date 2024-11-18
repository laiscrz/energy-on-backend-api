package com.taligado.energy.controller;

import com.taligado.energy.dto.EnderecoDTO;
import com.taligado.energy.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Endpoint para buscar todos os endereços
    @GetMapping
    public List<EnderecoDTO> getAllEnderecos() {
        return enderecoService.getAllEnderecos();
    }

    // Endpoint para buscar um endereço por ID
    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> getEnderecoById(@PathVariable Integer id) {
        EnderecoDTO endereco = enderecoService.getEnderecoById(id);
        return endereco != null ? ResponseEntity.ok(endereco) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo endereço
    @PostMapping
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO savedEndereco = enderecoService.saveEndereco(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEndereco);
    }

    // Endpoint para atualizar um endereço existente
    @PutMapping("/{id}")
    public ResponseEntity<EnderecoDTO> updateEndereco(@PathVariable Integer id, @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO updatedEndereco = enderecoService.updateEndereco(id, enderecoDTO);
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
