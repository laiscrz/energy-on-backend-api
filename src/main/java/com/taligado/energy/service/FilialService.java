package com.taligado.energy.service;

import com.taligado.energy.dto.FilialDTO;
import com.taligado.energy.exception.ResourceNotFoundException;
import com.taligado.energy.model.Filial;
import com.taligado.energy.model.Empresa;
import com.taligado.energy.model.Endereco;
import com.taligado.energy.repository.IFilialRepository;
import com.taligado.energy.repository.IEmpresaRepository;
import com.taligado.energy.repository.IEnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilialService {

    @Autowired
    private IFilialRepository filialRepository;

    @Autowired
    private IEmpresaRepository empresaRepository;

    @Autowired
    private IEnderecoRepository enderecoRepository;

    @Autowired
    private ProceduresService proceduresService;

    // Buscar todas as filiais e retornar como DTOs
    public List<FilialDTO> getAllFiliais() {
        List<Filial> filiais = filialRepository.findAll();
        return filiais.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar filial por ID e retornar como DTO
    public FilialDTO getFilialById(Integer id) {
        Optional<Filial> filial = filialRepository.findById(id);
        return filial.map(this::mapToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada com o ID: " + id));
    }

    // Salvar uma nova filial e retornar como DTO
    public FilialDTO saveFilial(FilialDTO filialDTO) {
       try {
           String resultadoProcedure = proceduresService.inserirFilialProcedure(filialDTO);

           if ("Filial criada com sucesso via PROCEDURE!".equals(resultadoProcedure)) {
               return filialDTO;
           } else {
               throw new RuntimeException("Erro ao inserir filial via procedure.");
           }
       } catch (Exception e) {
           throw new RuntimeException("Erro ao criar filial: " + e.getMessage(), e);

       }
    }

    public FilialDTO updateFilial(Integer id, FilialDTO filialDTO) {
        // Buscar a entidade existente
        Filial filialExistente = filialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filial não encontrada com o ID: " + id));

        // Atualizar os campos da entidade existente com os valores do DTO
        filialExistente.setNome(filialDTO.getNome());
        filialExistente.setTipo(filialDTO.getTipo());
        filialExistente.setCnpjFilial(filialDTO.getCnpjFilial());
        filialExistente.setAreaOperacional(filialDTO.getAreaOperacional());

        // Buscar e atualizar empresa e endereço, se necessário
        Optional<Empresa> empresa = empresaRepository.findById(filialDTO.getEmpresaId());
        Optional<Endereco> endereco = enderecoRepository.findById(filialDTO.getEnderecoId());

        empresa.ifPresent(filialExistente::setEmpresa);
        endereco.ifPresent(filialExistente::setEndereco);

        // Salvar a entidade atualizada
        Filial updatedFilial = filialRepository.save(filialExistente);

        return mapToDTO(updatedFilial);
    }


    // Excluir uma filial
    public void deleteFilial(Integer id) {
        if (!filialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Filial não encontrada com o ID: " + id);
        }
        filialRepository.deleteById(id);
    }

    // Método para converter Filial para FilialDTO
    private FilialDTO mapToDTO(Filial filial) {
        FilialDTO filialDTO = new FilialDTO();
        filialDTO.setIdfilial(filial.getIdfilial());
        filialDTO.setNome(filial.getNome());
        filialDTO.setTipo(filial.getTipo());
        filialDTO.setCnpjFilial(filial.getCnpjFilial());
        filialDTO.setAreaOperacional(filial.getAreaOperacional());
        filialDTO.setEmpresaId(filial.getEmpresa().getIdempresa());
        filialDTO.setEnderecoId(filial.getEndereco().getIdendereco());
        return filialDTO;
    }

    // Método para converter FilialDTO para Filial
    private Filial mapToEntity(FilialDTO filialDTO) {
        Filial filial = new Filial();
        filial.setNome(filialDTO.getNome());
        filial.setTipo(filialDTO.getTipo());
        filial.setCnpjFilial(filialDTO.getCnpjFilial());
        filial.setAreaOperacional(filialDTO.getAreaOperacional());

        // Buscar a empresa e o endereço pelos IDs e setar na entidade
        Optional<Empresa> empresa = empresaRepository.findById(filialDTO.getEmpresaId());
        Optional<Endereco> endereco = enderecoRepository.findById(filialDTO.getEnderecoId());

        empresa.ifPresent(filial::setEmpresa);
        endereco.ifPresent(filial::setEndereco);

        return filial;
    }
}
