package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "historico")
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idhistorico")
    private Integer idhistorico;

    @Column(name = "data_criacao")
    private Date dataCriacao;

    @Column(name = "valor_consumo_kwh")
    private Double valorConsumoKwh;

    @Column(name = "intensidade_carbono")
    private Double intensidadeCarbono;

    @Column(name = "custo_energia_estimado")
    private Double custoEnergiaEstimado;

    @ManyToOne
    @JoinColumn(name = "regulacao_energia_idregulacao", nullable = false)
    private RegulacaoEnergia regulacaoEnergia;

    // Relacionamento com Sensor
    @ManyToMany
    @JoinTable(
            name = "historico_sensor",
            joinColumns = @JoinColumn(name = "historico_idhistorico"),
            inverseJoinColumns = @JoinColumn(name = "sensor_idsensor"))
    private List<Sensor> sensores;

}


