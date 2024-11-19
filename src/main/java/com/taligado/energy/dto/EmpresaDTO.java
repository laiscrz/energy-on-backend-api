package com.taligado.energy.dto;

import lombok.Data;

import java.sql.Date;

@Data
public class EmpresaDTO {
    private Integer idempresa;
    private String nome;
    private String email;
    private String cnpj;
    private String segmento;
    private Date dataFundacao;

}
