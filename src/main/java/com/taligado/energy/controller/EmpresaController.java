package com.taligado.energy.controller;

import com.taligado.energy.dto.EmpresaDTO;
import com.taligado.energy.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/empresas")
@Tag(name = "Empresas", description = "Operações relacionadas a Empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Endpoint para buscar todas as empresas
    @GetMapping
    @Operation(
            summary = "Buscar todas as empresas",
            description = "Retorna uma lista de todas as empresas registradas."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<EmpresaDTO> getAllEmpresas() {
        return empresaService.getAllEmpresas();
    }

    // Endpoint para buscar uma empresa por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar empresa por ID",
            description = "Retorna a empresa correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<EmpresaDTO> getEmpresaById(@PathVariable Integer id) {
        EmpresaDTO empresa = empresaService.getEmpresaById(id);
        return empresa != null ? ResponseEntity.ok(empresa) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar uma nova empresa
    @PostMapping
    @Operation(
            summary = "Salvar nova empresa",
            description = "Cria uma nova empresa no sistema utilizando uma procedure do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<Object> createEmpresa(@RequestBody EmpresaDTO empresaDTO) {
        try {
            // Chama o método do serviço que salva a empresa usando a procedure
            EmpresaDTO savedEmpresa = empresaService.saveEmpresa(empresaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmpresa);
        } catch (Exception e) {
            // Retorna JSON com mensagem de erro
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Erro ao criar empresa", "details", e.getMessage()));
        }
    }

    // Endpoint para atualizar uma empresa existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar empresa existente",
            description = "Atualiza as informações de uma empresa já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<EmpresaDTO> updateEmpresa(@PathVariable Integer id, @RequestBody EmpresaDTO empresaDTO) {
        EmpresaDTO updatedEmpresa = empresaService.updateEmpresa(id, empresaDTO);
        return updatedEmpresa != null ? ResponseEntity.ok(updatedEmpresa) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir uma empresa
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir empresa",
            description = "Exclui uma empresa pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empresa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    public ResponseEntity<Void> deleteEmpresa(@PathVariable Integer id) {
        empresaService.deleteEmpresa(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
