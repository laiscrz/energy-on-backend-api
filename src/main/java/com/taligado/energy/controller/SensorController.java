package com.taligado.energy.controller;

import com.taligado.energy.dto.SensorDTO;
import com.taligado.energy.service.SensorService;
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
@RequestMapping("/api/sensores")
@Tag(name = "Sensores", description = "Operações relacionadas aos sensores de medição")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // Endpoint para buscar todos os sensores
    @GetMapping
    @Operation(
            summary = "Buscar todos os sensores",
            description = "Retorna uma lista de todos os sensores registrados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de sensores retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    public List<SensorDTO> getAllSensores() {
        return sensorService.getAllSensores();
    }

    // Endpoint para buscar um sensor por ID
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar sensor por ID",
            description = "Retorna o sensor correspondente ao ID fornecido."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor encontrado"),
            @ApiResponse(responseCode = "404", description = "Sensor não encontrado")
    })
    public ResponseEntity<SensorDTO> getSensorById(@PathVariable Integer id) {
        SensorDTO sensorDTO = sensorService.getSensorById(id);
        return sensorDTO != null ? ResponseEntity.ok(sensorDTO)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para salvar um novo sensor
    @PostMapping
    @Operation(
            summary = "Salvar novo sensor",
            description = "Cria um novo sensor no sistema utilizando uma procedure do banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sensor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados de entrada")
    })
    public ResponseEntity<Object> createSensor(@RequestBody SensorDTO sensorDTO) {
       try {
           SensorDTO savedSensorDTO = sensorService.saveSensor(sensorDTO);

           return new ResponseEntity<>(savedSensorDTO, HttpStatus.CREATED);
       } catch (Exception e) {
           // Retorna JSON com mensagem de erro
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                   .body(Map.of("error", "Erro ao criar sensor", "details", e.getMessage()));
       }
    }


    // Endpoint para atualizar um sensor existente
    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar sensor existente",
            description = "Atualiza as informações de um sensor já existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sensor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sensor não encontrado")
    })
    public ResponseEntity<SensorDTO> updateSensor(@PathVariable Integer id, @RequestBody SensorDTO sensorDTO) {
        SensorDTO updatedSensorDTO = sensorService.updateSensor(id, sensorDTO);
        return updatedSensorDTO != null ? ResponseEntity.ok(updatedSensorDTO)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Endpoint para excluir um sensor
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir sensor",
            description = "Exclui um sensor pelo ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sensor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Sensor não encontrado")
    })
    public ResponseEntity<Void> deleteSensor(@PathVariable Integer id) {
        sensorService.deleteSensor(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
