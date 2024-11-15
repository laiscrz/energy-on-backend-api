package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idendereco")
    private Integer idendereco;

    @Column(name = "logadouro", length = 50)
    private String logadouro;

    @Column(name = "cidade", length = 40)
    private String cidade;

    @Column(name = "estado", length = 40)
    private String estado;

    @Column(name = "cep", length = 20)
    private String cep;

    @Column(name = "pais", length = 40)
    private String pais;


}
