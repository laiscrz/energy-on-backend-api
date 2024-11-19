package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "dispositivo")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddispositivo")
    private Integer iddispositivo;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "tipo", length = 40)
    private String tipo;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "data_instalacao")
    private Date dataInstalacao;

    @ManyToOne
    @JoinColumn(name = "filial_idfilial", nullable = false)
    private Filial filial;

    @Column(name = "potencia_nominal")
    private Double potenciaNominal;

    // Relacionamento com Sensor
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dispositivo_sensor",
            joinColumns = @JoinColumn(name = "dispositivo_iddispositivo"),
            inverseJoinColumns = @JoinColumn(name = "sensor_idsensor"))
    private List<Sensor> sensores;

}


