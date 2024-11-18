package com.taligado.energy.service;

import com.taligado.energy.dto.DispositivoDTO;
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
    private ISensorRepository sensorRepository; // Repositório para buscar os sensores

    @Autowired
    private IFilialRepository filialRepository; // Repositório para buscar as filiais

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
        return dispositivo.map(this::mapToDTO).orElse(null); // Convertendo para DTO
    }

    // Salvar um novo dispositivo e retornar como DTO
    public DispositivoDTO saveDispositivo(DispositivoDTO dispositivoDTO) {
        Dispositivo dispositivo = mapToEntity(dispositivoDTO); // Convertendo DTO para entidade
        Dispositivo savedDispositivo = dispositivoRepository.save(dispositivo);
        return mapToDTO(savedDispositivo); // Convertendo a entidade salva para DTO
    }

    // Atualizar um dispositivo existente e retornar como DTO
    public DispositivoDTO updateDispositivo(Integer id, DispositivoDTO dispositivoDTO) {
        if (dispositivoRepository.existsById(id)) {
            dispositivoDTO.setIddispositivo(id);
            Dispositivo dispositivo = mapToEntity(dispositivoDTO); // Convertendo DTO para entidade
            Dispositivo updatedDispositivo = dispositivoRepository.save(dispositivo);
            return mapToDTO(updatedDispositivo); // Convertendo a entidade atualizada para DTO
        }
        return null;
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
        dispositivoDTO.setFilialId(dispositivo.getFilial().getIdfilial()); // Mapeando o ID da filial
        dispositivoDTO.setPotenciaNominal(dispositivo.getPotenciaNominal());

        // Mapeando a lista de sensores para uma lista de IDs
        List<Integer> sensoresIds = dispositivo.getSensores().stream()
                .map(Sensor::getIdsensor) // Obtendo o ID de cada sensor
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
                .map(sensorId -> sensorRepository.findById(sensorId).orElse(null)) // Buscando os sensores pelos IDs
                .collect(Collectors.toList());
        dispositivo.setSensores(sensores);

        return dispositivo;
    }
}
