package com.taligado.energy.service;

import com.taligado.energy.model.Dispositivo;
import com.taligado.energy.repository.IDispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {

    @Autowired
    private IDispositivoRepository dispositivoRepository;

    // Buscar todos os dispositivos
    public List<Dispositivo> getAllDispositivos() {
        return dispositivoRepository.findAll();
    }

    // Buscar dispositivo por ID
    public Optional<Dispositivo> getDispositivoById(Integer id) {
        return dispositivoRepository.findById(id);
    }

    // Salvar um novo dispositivo
    public Dispositivo saveDispositivo(Dispositivo dispositivo) {
        return dispositivoRepository.save(dispositivo);
    }

    // Atualizar um dispositivo existente
    public Dispositivo updateDispositivo(Integer id, Dispositivo dispositivoDetails) {
        if (dispositivoRepository.existsById(id)) {
            dispositivoDetails.setIddispositivo(id);
            return dispositivoRepository.save(dispositivoDetails);
        }
        return null;
    }

    // Excluir um dispositivo
    public void deleteDispositivo(Integer id) {
        dispositivoRepository.deleteById(id);
    }
}

