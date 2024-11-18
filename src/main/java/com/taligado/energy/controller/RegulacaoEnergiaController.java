package com.taligado.energy.controller;

import com.taligado.energy.dto.RegulacaoEnergiaDTO;
import com.taligado.energy.service.RegulacaoEnergiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/regulacoes-energia")
@Tag(name = "Regulações de Energia", description = "Operações relacionadas a regulamentações de energia")
public class RegulacaoEnergiaController {

    @Autowired
    private RegulacaoEnergiaService regulacaoEnergiaService;

    // Endpoint para buscar todas as regulações de energia
    @GetMapping
    @Operation(
            summary = "Buscar todas as regulações de energia",
            description = "Retorna uma lista de todas as regulações de energia registradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de regulações de energia retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<RegulacaoEnergiaDTO> getAllRegulacoesEnergia() {
        return regulacaoEnergiaService.getAllRegulacoes();
    }

    // Endpoint para buscar uma regulação de energia por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar regulação de energia por ID",
            description = "Retorna a regulação de energia correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regulação de energia encontrada"),
            @ApiResponse(responseCode = "404", description = "Regulação de energia não encontrada")
    })
    public ResponseEntity<RegulacaoEnergiaDTO> getRegulacaoEnergiaById(@PathVariable Integer id) {
        RegulacaoEnergiaDTO regulacaoEnergiaDTO = regulacaoEnergiaService.getRegulacaoById(id);
        return regulacaoEnergiaDTO != null ? ResponseEntity.ok(regulacaoEnergiaDTO)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar uma nova regulação de energia
    @PostMapping
    @Operation(
            summary = "Salvar nova regulação de energia",
            description = "Cria uma nova regulação de energia no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Regulação de energia criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<RegulacaoEnergiaDTO> createRegulacaoEnergia(@RequestBody RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        RegulacaoEnergiaDTO savedRegulacaoEnergiaDTO = regulacaoEnergiaService.saveRegulacao(regulacaoEnergiaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRegulacaoEnergiaDTO);
    }

    // Endpoint para atualizar uma regulação de energia existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar regulação de energia existente",
            description = "Atualiza as informações de uma regulação de energia já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Regulação de energia atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Regulação de energia não encontrada")
    })
    public ResponseEntity<RegulacaoEnergiaDTO> updateRegulacaoEnergia(@PathVariable Integer id, @RequestBody RegulacaoEnergiaDTO regulacaoEnergiaDTO) {
        RegulacaoEnergiaDTO updatedRegulacaoEnergiaDTO = regulacaoEnergiaService.updateRegulacao(id, regulacaoEnergiaDTO);
        return updatedRegulacaoEnergiaDTO != null ? ResponseEntity.ok(updatedRegulacaoEnergiaDTO) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir uma regulação de energia
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir regulação de energia",
            description = "Exclui uma regulação de energia pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Regulação de energia excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Regulação de energia não encontrada")
    })
    public ResponseEntity<Void> deleteRegulacaoEnergia(@PathVariable Integer id) {
        regulacaoEnergiaService.deleteRegulacao(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
