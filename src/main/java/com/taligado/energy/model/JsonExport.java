package com.taligado.energy.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import java.sql.Clob;

@Entity
public class JsonExport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "json_data")
    private Clob jsonData;


}

