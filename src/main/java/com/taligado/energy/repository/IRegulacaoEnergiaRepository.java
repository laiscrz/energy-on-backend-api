package com.taligado.energy.repository;

import com.taligado.energy.model.RegulacaoEnergia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegulacaoEnergiaRepository extends JpaRepository<RegulacaoEnergia, Integer> {
}
