package com.taligado.energy.service;

import com.taligado.energy.dto.DispositivoDTO;
import com.taligado.energy.exception.ResourceNotFoundException;
import com.taligado.energy.model.Dispositivo;
import com.taligado.energy.model.Sensor;
import com.taligado.energy.repository.IDispositivoRepository;
import com.taligado.energy.repository.ISensorRepository;
import com.taligado.energy.repository.IFilialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DispositivoService {

    @Autowired
    private IDispositivoRepository dispositivoRepository;

    @Autowired
    private ISensorRepository sensorRepository;

    @Autowired
    private IFilialRepository filialRepository;

    // Buscar todos os dispositivos e retornar como DTOs
    public List<DispositivoDTO> getAllDispositivos() {
        List<Dispositivo> dispositivos = dispositivoRepository.findAll();
        return dispositivos.stream()
                .map(this::mapToDTO)  // Convertendo cada dispositivo para DTO
                .collect(Collectors.toList());
    }

    // Buscar dispositivo por ID e retornar como DTO
    public DispositivoDTO getDispositivoById(Integer id) {
        Optional<Dispositivo> dispositivo = dispositivoRepository.findById(id);
        return dispositivo.map(this::mapToDTO).orElse(null);
    }

    // Salvar um novo dispositivo e retornar como DTO
    public DispositivoDTO saveDispositivo(DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivo = mapToEntity(dispositivoDTO);
        Dispositivo savedDispositivo = dispositivoRepository.save(dispositivo);
        return mapToDTO(savedDispositivo);
    }

    public DispositivoDTO updateDispositivo(Integer id, DispositivoDTO dispositivoDTO) {
        // Buscar o dispositivo existente
        Dispositivo dispositivoExistente = dispositivoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispositivo não encontrado com o ID: " + id));

        // Atualizar os campos do dispositivo existente
        dispositivoExistente.setNome(dispositivoDTO.getNome());
        dispositivoExistente.setTipo(dispositivoDTO.getTipo());
        dispositivoExistente.setStatus(dispositivoDTO.getStatus());
        dispositivoExistente.setDataInstalacao(dispositivoDTO.getDataInstalacao());
        dispositivoExistente.setPotenciaNominal(dispositivoDTO.getPotenciaNominal());

        // Atualizar a filial
        dispositivoExistente.setFilial(filialRepository.findById(dispositivoDTO.getFilialId())
                .orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada com o ID: " + dispositivoDTO.getFilialId())));

        // Atualizar a lista de sensores
        List<Sensor> sensores = dispositivoDTO.getSensoresIds().stream()
                .map(sensorId -> sensorRepository.findById(sensorId)
                        .orElseThrow(() -> new ResourceNotFoundException("Sensor não encontrado com o ID: " + sensorId)))
                .collect(Collectors.toList());
        dispositivoExistente.setSensores(sensores);

        // Salvar a entidade atualizada
        Dispositivo updatedDispositivo = dispositivoRepository.save(dispositivoExistente);

        return mapToDTO(updatedDispositivo);
    }


    // Excluir um dispositivo
    public void deleteDispositivo(Integer id) {
        dispositivoRepository.deleteById(id);
    }

    // Método de mapeamento de entidade para DTO
    private DispositivoDTO mapToDTO(Dispositivo dispositivo) {
        DispositivoDTO dispositivoDTO = new DispositivoDTO();
        dispositivoDTO.setIddispositivo(dispositivo.getIddispositivo());
        dispositivoDTO.setNome(dispositivo.getNome());
        dispositivoDTO.setTipo(dispositivo.getTipo());
        dispositivoDTO.setStatus(dispositivo.getStatus());
        dispositivoDTO.setDataInstalacao(dispositivo.getDataInstalacao());
        dispositivoDTO.setFilialId(dispositivo.getFilial().getIdfilial());
        dispositivoDTO.setPotenciaNominal(dispositivo.getPotenciaNominal());

        // Mapeando a lista de sensores para uma lista de IDs
        List<Integer> sensoresIds = dispositivo.getSensores().stream()
                .map(Sensor::getIdsensor)
                .collect(Collectors.toList());
        dispositivoDTO.setSensoresIds(sensoresIds);

        return dispositivoDTO;
    }

    // Método de mapeamento de DTO para entidade
    private Dispositivo mapToEntity(DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivo = new Dispositivo();
        dispositivo.setNome(dispositivoDTO.getNome());
        dispositivo.setTipo(dispositivoDTO.getTipo());
        dispositivo.setStatus(dispositivoDTO.getStatus());
        dispositivo.setDataInstalacao(dispositivoDTO.getDataInstalacao());
        dispositivo.setPotenciaNominal(dispositivoDTO.getPotenciaNominal());

        // Buscar a filial pelo ID
        dispositivo.setFilial(filialRepository.findById(dispositivoDTO.getFilialId()).orElse(null));

        // Mapeando a lista de sensores IDs para objetos de sensor
        List<Sensor> sensores = dispositivoDTO.getSensoresIds().stream()
                .map(sensorId -> sensorRepository.findById(sensorId).orElse(null))
                .collect(Collectors.toList());
        dispositivo.setSensores(sensores);

        return dispositivo;
    }
}
