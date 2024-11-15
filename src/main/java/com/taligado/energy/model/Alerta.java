package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idalerta")
    private Integer idalerta;

    @Column(name = "descricao", length = 100)
    private String descricao;

    @Column(name = "severidade", length = 20)
    private String severidade;

    @Column(name = "data_alerta")
    private Date dataAlerta;

    @ManyToOne
    @JoinColumn(name = "sensor_idsensor", nullable = false)
    private Sensor sensor;

}
