package com.example.msempresa.service;

import com.example.msempresa.entity.Empresa;


import java.util.List;
import java.util.Optional;

public interface EmpresaService {
    public List<Empresa> list();

    public Optional<Empresa> getById(Integer id);

    public Empresa save(Empresa empresa);

    public Empresa update(Integer id, Empresa empresa);

    public void delete(Integer id);
}
