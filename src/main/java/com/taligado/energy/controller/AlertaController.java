package com.taligado.energy.controller;

import com.taligado.energy.dto.AlertaDTO;
import com.taligado.energy.service.AlertaService;
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
@RequestMapping("/api/alertas")
@Tag(name = "Alertas", description = "Operações relacionadas a Alertas")
public class AlertaController {

    @Autowired
    private AlertaService alertaService;

    // Endpoint para buscar todos os alertas
    @GetMapping
    @Operation(
            summary = "Buscar todos os alertas",
            description = "Retorna uma lista de todos os alertas registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alertas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<AlertaDTO> getAllAlertas() {
        return alertaService.getAllAlertas();
    }

    // Endpoint para buscar um alerta por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar alerta por ID",
            description = "Retorna o alerta correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta encontrado"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    public ResponseEntity<AlertaDTO> getAlertaById(@PathVariable Integer id) {
        AlertaDTO alerta = alertaService.getAlertaById(id);
        return alerta != null ? ResponseEntity.ok(alerta) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo alerta
    @PostMapping
    @Operation(
            summary = "Salvar novo alerta",
            description = "Cria um novo alerta no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alerta criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<AlertaDTO> createAlerta(@RequestBody AlertaDTO alertaDTO) {
        AlertaDTO savedAlerta = alertaService.saveAlerta(alertaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAlerta);
    }

    // Endpoint para atualizar um alerta existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar alerta existente",
            description = "Atualiza as informações de um alerta já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerta atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    public ResponseEntity<AlertaDTO> updateAlerta(@PathVariable Integer id, @RequestBody AlertaDTO alertaDetails) {
        AlertaDTO updatedAlerta = alertaService.updateAlerta(id, alertaDetails);
        return updatedAlerta != null ? ResponseEntity.ok(updatedAlerta) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir um alerta
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir alerta",
            description = "Exclui um alerta pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alerta excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alerta não encontrado")
    })
    public ResponseEntity<Void> deleteAlerta(@PathVariable Integer id) {
        alertaService.deleteAlerta(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
