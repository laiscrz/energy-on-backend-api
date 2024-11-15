package com.taligado.energy.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "filial")
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfilial")
    private Integer idfilial;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "tipo", length = 40)
    private String tipo;

    @Column(name = "cnpj_filial", length = 20)
    private String cnpjFilial;

    @Column(name = "area_operacional", length = 40)
    private String areaOperacional;

    @ManyToOne
    @JoinColumn(name = "empresa_idempresa", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "endereco_idendereco", nullable = false)
    private Endereco endereco;

}
