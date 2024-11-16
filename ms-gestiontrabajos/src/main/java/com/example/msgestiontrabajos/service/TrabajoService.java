package com.example.msgestiontrabajos.service;

import com.example.msgestiontrabajos.dto.TrabajoDto;
import com.example.msgestiontrabajos.entity.Trabajo;

import java.util.List;
import java.util.Optional;

public interface TrabajoService {
    List<Trabajo> list();
    Optional<Trabajo> getById(Integer id);
    Trabajo save(Trabajo trabajo);
    Trabajo update(Trabajo trabajo);
    void delete(Integer id);
    List<Trabajo> findByEmpresaId(Integer empresaId);

    // Nuevo método para obtener trabajo junto con la información de la empresa
    TrabajoDto obtenerTrabajoConEmpresa(Integer id);
}
