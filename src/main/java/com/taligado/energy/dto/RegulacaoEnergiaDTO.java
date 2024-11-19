package com.taligado.energy.dto;

import lombok.Data;

import java.sql.Date;


@Data
public class RegulacaoEnergiaDTO {
    private Integer idregulacao;
    private Double tarifaKwh;
    private String nomeBandeira;
    private Double tarifaAdicionalBandeira;
    private Date dataAtualizacao;
}
