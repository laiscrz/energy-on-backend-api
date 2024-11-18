package com.taligado.energy.controller;

import com.taligado.energy.dto.FilialDTO;
import com.taligado.energy.service.FilialService;
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
@RequestMapping("/api/filiais")
@Tag(name = "Filiais", description = "Operações relacionadas a Filiais")
public class FilialController {

    @Autowired
    private FilialService filialService;

    // Endpoint para buscar todas as filiais
    @GetMapping
    @Operation(
            summary = "Buscar todas as filiais",
            description = "Retorna uma lista de todas as filiais registradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de filiais retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<FilialDTO> getAllFiliais() {
        return filialService.getAllFiliais();
    }

    // Endpoint para buscar uma filial por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar filial por ID",
            description = "Retorna a filial correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filial encontrada"),
            @ApiResponse(responseCode = "404", description = "Filial não encontrada")
    })
    public ResponseEntity<FilialDTO> getFilialById(@PathVariable Integer id) {
        FilialDTO filial = filialService.getFilialById(id);
        return filial != null ? ResponseEntity.ok(filial) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar uma nova filial
    @PostMapping
    @Operation(
            summary = "Salvar nova filial",
            description = "Cria uma nova filial no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Filial criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<FilialDTO> createFilial(@RequestBody FilialDTO filialDTO) {
        FilialDTO savedFilial = filialService.saveFilial(filialDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFilial);
    }

    // Endpoint para atualizar uma filial existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar filial existente",
            description = "Atualiza as informações de uma filial já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filial atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filial não encontrada")
    })
    public ResponseEntity<FilialDTO> updateFilial(@PathVariable Integer id, @RequestBody FilialDTO filialDTO) {
        FilialDTO updatedFilial = filialService.updateFilial(id, filialDTO);
        return updatedFilial != null ? ResponseEntity.ok(updatedFilial) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir uma filial
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir filial",
            description = "Exclui uma filial pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Filial excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Filial não encontrada")
    })
    public ResponseEntity<Void> deleteFilial(@PathVariable Integer id) {
        filialService.deleteFilial(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
