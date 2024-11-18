package com.taligado.energy.dto;

import lombok.Data;

import java.util.List;

@Data
public class SensorDTO {

    private Integer idsensor;
    private String tipo;
    private String descricao;
    private String unidade;
    private Double valorAtual;
    private Double tempoOperacao;
    private List<Integer> dispositivosIds;
    private List<Integer> historicosIds;
}
