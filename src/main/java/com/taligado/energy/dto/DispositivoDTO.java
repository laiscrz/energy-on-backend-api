package com.taligado.energy.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class DispositivoDTO {
    private Integer iddispositivo;
    private String nome;
    private String tipo;
    private String status;
    private Date dataInstalacao;
    private Integer filialId;
    private Double potenciaNominal;
    private List<Integer> sensoresIds;

}
