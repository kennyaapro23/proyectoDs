package com.example.msempresa.service;

import com.example.msempresa.dto.GestiontrabajosDto; // Asegúrate de importar esta clase
import com.example.msempresa.entity.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaService {
    List<Empresa> list();

    Optional<Empresa> getById(Integer id);

    Empresa save(Empresa empresa);

    Empresa update(Integer id, Empresa empresa);

    void delete(Integer id);

    // Método para publicar un trabajo
    GestiontrabajosDto publicarTrabajoDesdeEmpresa(GestiontrabajosDto trabajoDto, Integer empresaId);
}
