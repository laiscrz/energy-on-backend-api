package com.taligado.energy.repository;

import com.taligado.energy.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAlertaRepository extends JpaRepository<Alerta, Integer> {
}

