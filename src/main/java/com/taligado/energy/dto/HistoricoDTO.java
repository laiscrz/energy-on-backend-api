package com.taligado.energy.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HistoricoDTO {
    private Integer idhistorico;
    private Date dataCriacao;
    private Double valorConsumoKwh;
    private Double intensidadeCarbono;
    private Double custoEnergiaEstimado;
    private Integer regulacaoEnergiaId;
    private List<Integer> sensoresIds;

}
