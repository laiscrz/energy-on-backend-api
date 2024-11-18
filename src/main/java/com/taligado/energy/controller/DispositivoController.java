package com.taligado.energy.controller;

import com.taligado.energy.dto.DispositivoDTO;
import com.taligado.energy.service.DispositivoService;
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
@RequestMapping("/api/dispositivos")
@Tag(name = "Dispositivos", description = "Operações relacionadas a Dispositivos")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    // Endpoint para buscar todos os dispositivos
    @GetMapping
    @Operation(
            summary = "Buscar todos os dispositivos",
            description = "Retorna uma lista de todos os dispositivos registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de dispositivos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<DispositivoDTO> getAllDispositivos() {
        return dispositivoService.getAllDispositivos();
    }

    // Endpoint para buscar um dispositivo por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar dispositivo por ID",
            description = "Retorna o dispositivo correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo encontrado"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    public ResponseEntity<DispositivoDTO> getDispositivoById(@PathVariable Integer id) {
        DispositivoDTO dispositivoDTO = dispositivoService.getDispositivoById(id);
        return dispositivoDTO != null ? ResponseEntity.ok(dispositivoDTO) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo dispositivo
    @PostMapping
    @Operation(
            summary = "Salvar novo dispositivo",
            description = "Cria um novo dispositivo no sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dispositivo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<DispositivoDTO> createDispositivo(@RequestBody DispositivoDTO dispositivoDTO) {
        DispositivoDTO savedDispositivoDTO = dispositivoService.saveDispositivo(dispositivoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDispositivoDTO);
    }

    // Endpoint para atualizar um dispositivo existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar dispositivo existente",
            description = "Atualiza as informações de um dispositivo já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dispositivo atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    public ResponseEntity<DispositivoDTO> updateDispositivo(@PathVariable Integer id, @RequestBody DispositivoDTO dispositivoDTO) {
        DispositivoDTO updatedDispositivoDTO = dispositivoService.updateDispositivo(id, dispositivoDTO);
        return updatedDispositivoDTO != null ? ResponseEntity.ok(updatedDispositivoDTO) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir um dispositivo
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir dispositivo",
            description = "Exclui um dispositivo pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dispositivo excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Dispositivo não encontrado")
    })
    public ResponseEntity<Void> deleteDispositivo(@PathVariable Integer id) {
        dispositivoService.deleteDispositivo(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
