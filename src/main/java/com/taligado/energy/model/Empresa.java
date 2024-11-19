package com.taligado.energy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;


@Data
@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_fundacao")
    private Date dataFundacao;

}

