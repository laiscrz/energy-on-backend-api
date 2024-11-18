package com.taligado.energy.service;

import com.taligado.energy.dto.EnderecoDTO;
import com.taligado.energy.exception.ResourceNotFoundException;
import com.taligado.energy.model.Endereco;
import com.taligado.energy.repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoRepository enderecoRepository;

    // Buscar todos os endereços e retornar como DTOs
    public List<EnderecoDTO> getAllEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar endereço por ID e retornar como DTO
    public EnderecoDTO getEnderecoById(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        return endereco.map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + id));
    }

    // Salvar um novo endereço e retornar como DTO
    public EnderecoDTO saveEndereco(EnderecoDTO enderecoDTO) {
        Endereco endereco = mapToEntity(enderecoDTO);
        Endereco savedEndereco = enderecoRepository.save(endereco);
        return mapToDTO(savedEndereco);
    }

    public EnderecoDTO updateEndereco(Integer id, EnderecoDTO enderecoDTO) {
        // Buscar o endereço existente
        Endereco enderecoExistente = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com o ID: " + id));

        // Atualizar os campos do endereço existente com os valores do DTO
        enderecoExistente.setLogadouro(enderecoDTO.getLogadouro());
        enderecoExistente.setCidade(enderecoDTO.getCidade());
        enderecoExistente.setEstado(enderecoDTO.getEstado());
        enderecoExistente.setCep(enderecoDTO.getCep());
        enderecoExistente.setPais(enderecoDTO.getPais());

        // Salvar a entidade atualizada
        Endereco updatedEndereco = enderecoRepository.save(enderecoExistente);

        return mapToDTO(updatedEndereco);
    }


    // Excluir um endereço
    public void deleteEndereco(Integer id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado com o ID: " + id);
        }
        enderecoRepository.deleteById(id);
    }

    // Método para converter Endereco para EnderecoDTO
    private EnderecoDTO mapToDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdendereco(endereco.getIdendereco());
        enderecoDTO.setLogadouro(endereco.getLogadouro());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setPais(endereco.getPais());
        return enderecoDTO;
    }

    // Método para converter EnderecoDTO para Endereco
    private Endereco mapToEntity(EnderecoDTO enderecoDTO) {
        Endereco endereco = new Endereco();
        endereco.setLogadouro(enderecoDTO.getLogadouro());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCep(enderecoDTO.getCep());
        endereco.setPais(enderecoDTO.getPais());
        return endereco;
    }
}
