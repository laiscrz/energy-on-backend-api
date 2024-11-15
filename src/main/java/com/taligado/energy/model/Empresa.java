package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idempresa")
    private Integer idempresa;

    @Column(name = "nome", length = 50, nullable = false)
    private String nome;

    @Column(name = "email", length = 40)
    private String email;

    @Column(name = "cnpj", length = 20)
    private String cnpj;

    @Column(name = "segmento", length = 40)
    private String segmento;

    @Column(name = "data_fundacao")
    private Date dataFundacao;

}

