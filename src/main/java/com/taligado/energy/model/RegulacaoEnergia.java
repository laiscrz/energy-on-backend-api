package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;


@Data
@Entity
@Table(name = "regulacao_energia")
public class RegulacaoEnergia {

    @Id
    @Column(name = "idregulacao")
    private Integer idregulacao;

    @Column(name = "tarifa_kwh")
    private Double tarifaKwh;

    @Column(name = "nome_bandeira", length = 50)
    private String nomeBandeira;

    @Column(name = "tarifa_adicional_bandeira")
    private Double tarifaAdicionalBandeira;

    @Column(name = "data_atualizacao")
    private Date dataAtualizacao;

}
