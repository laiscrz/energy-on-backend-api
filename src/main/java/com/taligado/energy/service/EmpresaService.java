package com.taligado.energy.service;

import com.taligado.energy.dto.EmpresaDTO;
import com.taligado.energy.model.Empresa;
import com.taligado.energy.repository.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired
    private IEmpresaRepository empresaRepository;

    // Buscar todas as empresas e retornar como DTOs
    public List<EmpresaDTO> getAllEmpresas() {
        List<Empresa> empresas = empresaRepository.findAll();
        return empresas.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Buscar empresa por ID e retornar como DTO
    public EmpresaDTO getEmpresaById(Integer id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        return empresa.map(this::mapToDTO).orElse(null); // Se não encontrar, retorna null
    }

    // Salvar uma nova empresa e retornar como DTO
    public EmpresaDTO saveEmpresa(EmpresaDTO empresaDTO) {
        Empresa empresa = mapToEntity(empresaDTO);
        Empresa savedEmpresa = empresaRepository.save(empresa);
        return mapToDTO(savedEmpresa); // Retorna o DTO da empresa salva
    }

    // Atualizar uma empresa existente e retornar como DTO
    public EmpresaDTO updateEmpresa(Integer id, EmpresaDTO empresaDTO) {
        if (empresaRepository.existsById(id)) {
            empresaDTO.setIdempresa(id);
            Empresa empresa = mapToEntity(empresaDTO);
            Empresa updatedEmpresa = empresaRepository.save(empresa);
            return mapToDTO(updatedEmpresa); // Retorna o DTO da empresa atualizada
        }
        return null;
    }

    // Excluir uma empresa
    public void deleteEmpresa(Integer id) {
        empresaRepository.deleteById(id);
    }

    // Método para converter Empresa para EmpresaDTO
    private EmpresaDTO mapToDTO(Empresa empresa) {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setIdempresa(empresa.getIdempresa());
        empresaDTO.setNome(empresa.getNome());
        empresaDTO.setEmail(empresa.getEmail());
        empresaDTO.setCnpj(empresa.getCnpj());
        empresaDTO.setSegmento(empresa.getSegmento());
        empresaDTO.setDataFundacao(empresa.getDataFundacao());
        return empresaDTO;
    }

    // Método para converter EmpresaDTO para Empresa
    private Empresa mapToEntity(EmpresaDTO empresaDTO) {
        Empresa empresa = new Empresa();
        empresa.setNome(empresaDTO.getNome());
        empresa.setEmail(empresaDTO.getEmail());
        empresa.setCnpj(empresaDTO.getCnpj());
        empresa.setSegmento(empresaDTO.getSegmento());
        empresa.setDataFundacao(empresaDTO.getDataFundacao());
        return empresa;
    }
}
