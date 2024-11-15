package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(mappedBy = "sensores")
    private List<Dispositivo> dispositivos;

    @ManyToMany(mappedBy = "sensores")
    private List<Historico> historicos;

}

