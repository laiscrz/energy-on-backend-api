package com.taligado.energy.repository;

import com.taligado.energy.model.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDispositivoRepository extends JpaRepository<Dispositivo, Integer> {
}

