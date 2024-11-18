package com.taligado.energy.config;

import java.util.List;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    OpenAPI customAPI() {
        return new OpenAPI()
                .info(new Info().title("TaLigado - EnergyAPI")
                        .description("API - Gestão de Consumo Energia e Emissões de Carbono")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(List.of(
                        new Server().url("https://localhost:8080")
                                .description("Servidor Local para desenvolvimento")))
                .tags(List.of(
                        new Tag().name("Empresas").description("Operações relacionadas a Empresas"),
                        new Tag().name("Filiais").description("Operações relacionadas a Filiais"),
                        new Tag().name("Endereços").description("Operações relacionadas a Endereços"),
                        new Tag().name("Dispositivos").description("Operações relacionadas a Dispositivos"),
                        new Tag().name("Sensores").description("Operações relacionadas a Sensores"),
                        new Tag().name("Alertas").description("Operações relacionadas a Alertas"),
                        new Tag().name("Regulações de Energia").description("Operações relacionadas a Reguladores de Energia"),
                        new Tag().name("Históricos").description("Operações relacionadas ao Histórico de Consumo e Emissões")));
    }
}