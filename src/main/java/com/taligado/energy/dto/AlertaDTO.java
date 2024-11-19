package com.taligado.energy.dto;

import lombok.Data;

import java.sql.Date;


@Data
public class AlertaDTO {
    private Integer idalerta;
    private String descricao;
    private String severidade;
    private Date dataAlerta;
    private Integer sensorId;


}
