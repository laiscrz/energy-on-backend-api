package com.taligado.energy.controller;

import com.taligado.energy.dto.HistoricoDTO;
import com.taligado.energy.service.HistoricoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/historicos")
@Tag(name = "Históricos", description = "Operações relacionadas ao histórico de consumo de energia")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    // Endpoint para buscar todos os históricos
    @GetMapping
    @Operation(
            summary = "Buscar todos os históricos",
            description = "Retorna uma lista de todos os históricos de consumo de energia registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de históricos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<HistoricoDTO> getAllHistoricos() {
        return historicoService.getAllHistoricos();
    }

    // Endpoint para buscar um histórico por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar histórico por ID",
            description = "Retorna o histórico de consumo correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico encontrado"),
            @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
    })
    public ResponseEntity<HistoricoDTO> getHistoricoById(@PathVariable Integer id) {
        Optional<HistoricoDTO> historico = Optional.ofNullable(historicoService.getHistoricoById(id));
        return historico.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Endpoint para salvar um novo histórico
    @PostMapping
    @Operation(
            summary = "Salvar novo histórico",
            description = "Cria um novo histórico de consumo de energia no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Histórico criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<HistoricoDTO> createHistorico(@RequestBody HistoricoDTO historicoDTO) {
        HistoricoDTO createdHistorico = historicoService.saveHistorico(historicoDTO);
        return new ResponseEntity<>(createdHistorico, HttpStatus.CREATED);
    }

    // Endpoint para atualizar um histórico existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar histórico existente",
            description = "Atualiza as informações de um histórico de consumo de energia já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
    })
    public ResponseEntity<HistoricoDTO> updateHistorico(@PathVariable Integer id, @RequestBody HistoricoDTO historicoDetailsDTO) {
        HistoricoDTO updatedHistorico = historicoService.updateHistorico(id, historicoDetailsDTO);
        return updatedHistorico != null ? ResponseEntity.ok(updatedHistorico)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir um histórico
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir histórico",
            description = "Exclui um histórico de consumo de energia pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Histórico excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Histórico não encontrado")
    })
    public ResponseEntity<Void> deleteHistorico(@PathVariable Integer id) {
        historicoService.deleteHistorico(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
