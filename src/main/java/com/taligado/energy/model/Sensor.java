package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(name = "idsensor")
    private Integer idsensor;

    @Column(name = "tipo", length = 50)
    private String tipo;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "unidade", length = 20)
    private String unidade;

    @Column(name = "valor_atual")
    private Double valorAtual;

    @Column(name = "tempo_operacao")
    private Double tempoOperacao;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dispositivo_sensor",
            joinColumns = @JoinColumn(name = "dispositivo_iddispositivo"),
            inverseJoinColumns = @JoinColumn(name = "sensor_idsensor"))
    private List<Dispositivo> dispositivos;

    @ManyToMany
    @JoinTable(
            name = "historico_sensor",
            joinColumns = @JoinColumn(name = "historico_idhistorico"),
            inverseJoinColumns = @JoinColumn(name = "sensor_idsensor"))
    private List<Historico> historicos;


}

