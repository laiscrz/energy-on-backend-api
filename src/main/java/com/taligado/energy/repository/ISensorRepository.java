package com.taligado.energy.repository;

import com.taligado.energy.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISensorRepository extends JpaRepository<Sensor, Integer> {
}

