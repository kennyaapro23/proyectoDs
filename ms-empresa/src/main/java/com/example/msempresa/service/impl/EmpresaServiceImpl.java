package com.example.msempresa.service.impl;

import com.example.msempresa.entity.Empresa;
import com.example.msempresa.repository.EmpresaRepository;
import com.example.msempresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public List<Empresa> list() {
        return empresaRepository.findAll();
    }

    @Override
    public Optional<Empresa> getById(Integer id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa update(Integer id, Empresa user) {
        user.setId(id);
        return empresaRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        empresaRepository.deleteById(id);
    }
}
