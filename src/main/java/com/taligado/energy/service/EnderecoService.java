package com.taligado.energy.service;

import com.taligado.energy.model.Endereco;
import com.taligado.energy.repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private IEnderecoRepository enderecoRepository;

    // Buscar todos os endereços
    public List<Endereco> getAllEnderecos() {
        return enderecoRepository.findAll();
    }

    // Buscar endereço por ID
    public Optional<Endereco> getEnderecoById(Integer id) {
        return enderecoRepository.findById(id);
    }

    // Salvar um novo endereço
    public Endereco saveEndereco(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    // Atualizar um endereço existente
    public Endereco updateEndereco(Integer id, Endereco enderecoDetails) {
        if (enderecoRepository.existsById(id)) {
            enderecoDetails.setIdendereco(id);
            return enderecoRepository.save(enderecoDetails);
        }
        return null;
    }

    // Excluir um endereço
    public void deleteEndereco(Integer id) {
        enderecoRepository.deleteById(id);
    }
}

