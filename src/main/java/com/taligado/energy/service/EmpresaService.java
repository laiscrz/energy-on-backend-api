package com.taligado.energy.service;

import com.taligado.energy.model.Empresa;
import com.taligado.energy.repository.IEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {

    @Autowired
    private IEmpresaRepository empresaRepository;

    // Buscar todas as empresas
    public List<Empresa> getAllEmpresas() {
        return empresaRepository.findAll();
    }

    // Buscar empresa por ID
    public Optional<Empresa> getEmpresaById(Integer id) {
        return empresaRepository.findById(id);
    }

    // Salvar uma nova empresa
    public Empresa saveEmpresa(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    // Atualizar uma empresa existente
    public Empresa updateEmpresa(Integer id, Empresa empresaDetails) {
        if (empresaRepository.existsById(id)) {
            empresaDetails.setIdempresa(id);
            return empresaRepository.save(empresaDetails);
        }
        return null;
    }

    // Excluir uma empresa
    public void deleteEmpresa(Integer id) {
        empresaRepository.deleteById(id);
    }
}

