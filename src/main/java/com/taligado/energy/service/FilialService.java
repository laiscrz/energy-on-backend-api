package com.taligado.energy.service;

import com.taligado.energy.model.Filial;
import com.taligado.energy.repository.IFilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilialService {

    @Autowired
    private IFilialRepository filialRepository;

    // Buscar todas as filiais
    public List<Filial> getAllFiliais() {
        return filialRepository.findAll();
    }

    // Buscar filial por ID
    public Optional<Filial> getFilialById(Integer id) {
        return filialRepository.findById(id);
    }

    // Salvar uma nova filial
    public Filial saveFilial(Filial filial) {
        return filialRepository.save(filial);
    }

    // Atualizar uma filial existente
    public Filial updateFilial(Integer id, Filial filialDetails) {
        if (filialRepository.existsById(id)) {
            filialDetails.setIdfilial(id);
            return filialRepository.save(filialDetails);
        }
        return null;
    }

    // Excluir uma filial
    public void deleteFilial(Integer id) {
        filialRepository.deleteById(id);
    }
}

