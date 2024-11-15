package com.taligado.energy.repository;

import com.taligado.energy.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IHistoricoRepository extends JpaRepository<Historico, Integer> {
}