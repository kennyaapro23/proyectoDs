package com.example.msempresa.service.impl;

import com.example.msempresa.entity.Empresa;
import com.example.msempresa.feign.UserFeign;
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

    @Autowired
    private UserFeign userFeign;

    @Override
    public List<Empresa> list() {
        return empresaRepository.findAll();
    }

    @Override
    public Optional<Empresa> getById(Integer id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        empresa.get().setUserDto(userFeign.getById(empresa.get().getUserid()).getBody());
        return empresa;
    }


    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa update(Integer id, Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @Override
    public void delete(Integer id) {
        empresaRepository.deleteById(id);
    }
}
