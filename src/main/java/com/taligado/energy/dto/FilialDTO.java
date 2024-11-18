package com.taligado.energy.dto;

import lombok.Data;

@Data
public class FilialDTO {
    private Integer idfilial;
    private String nome;
    private String tipo;
    private String cnpjFilial;
    private String areaOperacional;
    private Integer empresaId;
    private Integer enderecoId;

}
