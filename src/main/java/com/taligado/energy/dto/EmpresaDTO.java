package com.taligado.energy.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmpresaDTO {
    private Integer idempresa;
    private String nome;
    private String email;
    private String cnpj;
    private String segmento;
    private Date dataFundacao;

}
