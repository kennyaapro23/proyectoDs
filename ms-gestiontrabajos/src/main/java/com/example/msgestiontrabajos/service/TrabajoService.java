package com.example.msgestiontrabajos.service;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import com.example.msgestiontrabajos.entity.Trabajo;

import java.util.List;
import java.util.Optional;

public interface TrabajoService {

    // Listar todos los trabajos en estado ACTIVO
    List<Trabajo> list();

    // Obtener un trabajo por su ID
    Optional<Trabajo> getById(Integer id);

    // Guardar un nuevo trabajo
    Trabajo save(Trabajo trabajo);

    // Actualizar un trabajo existente
    Trabajo update(Trabajo trabajo);

    // Eliminar un trabajo por su ID
    void delete(Integer id);

    // Listar trabajos por empresa y en estado ACTIVO
    List<Trabajo> findByEmpresaId(Integer empresaId);

    // Obtener detalles de un trabajo junto con la información de la empresa
    EmpresaDto obtenerTrabajoConEmpresa(Integer id);
}