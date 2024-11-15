package com.taligado.energy.repository;

import com.taligado.energy.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFilialRepository extends JpaRepository<Filial, Integer> {
}

