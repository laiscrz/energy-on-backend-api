package com.taligado.energy.controller;

import com.taligado.energy.dto.EnderecoDTO;
import com.taligado.energy.service.EnderecoService;
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
@RequestMapping("/api/enderecos")
@Tag(name = "Endereços", description = "Operações relacionadas a Endereços")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    // Endpoint para buscar todos os endereços
    @GetMapping
    @Operation(
            summary = "Buscar todos os endereços",
            description = "Retorna uma lista de todos os endereços registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<EnderecoDTO> getAllEnderecos() {
        return enderecoService.getAllEnderecos();
    }

    // Endpoint para buscar um endereço por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar endereço por ID",
            description = "Retorna o endereço correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<EnderecoDTO> getEnderecoById(@PathVariable Integer id) {
        EnderecoDTO endereco = enderecoService.getEnderecoById(id);
        return endereco != null ? ResponseEntity.ok(endereco) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo endereço
    @PostMapping
    @Operation(
            summary = "Salvar novo endereço",
            description = "Cria um novo endereço no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<EnderecoDTO> createEndereco(@RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO savedEndereco = enderecoService.saveEndereco(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEndereco);
    }

    // Endpoint para atualizar um endereço existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar endereço existente",
            description = "Atualiza as informações de um endereço já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<EnderecoDTO> updateEndereco(@PathVariable Integer id, @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO updatedEndereco = enderecoService.updateEndereco(id, enderecoDTO);
        return updatedEndereco != null ? ResponseEntity.ok(updatedEndereco) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir um endereço
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir endereço",
            description = "Exclui um endereço pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    public ResponseEntity<Void> deleteEndereco(@PathVariable Integer id) {
        enderecoService.deleteEndereco(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
