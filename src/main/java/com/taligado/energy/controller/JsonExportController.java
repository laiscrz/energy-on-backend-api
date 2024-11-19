package com.taligado.energy.controller;

import com.taligado.energy.service.JsonExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/export")
public class JsonExportController {

    @Autowired
    private JsonExportService jsonExportService;

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public void exportarJson(HttpServletResponse response) throws IOException {
        try {
            // Obtém o JSON gerado pela procedure
            String jsonString = jsonExportService.exportarJson();

            // Configura o cabeçalho HTTP para forçar o download
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=\"reports_export.json\"");

            // Instancia o ObjectMapper para formatar o JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            // Converte e escreve o JSON no OutputStream da resposta
            objectMapper.writeValue(response.getOutputStream(), objectMapper.readTree(jsonString));

            // Garante que todos os dados sejam enviados
            response.flushBuffer();
        } catch (Exception e) {
            // Retorna uma mensagem de erro apropriada
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Erro ao exportar JSON: " + e.getMessage());
        }
    }
}

