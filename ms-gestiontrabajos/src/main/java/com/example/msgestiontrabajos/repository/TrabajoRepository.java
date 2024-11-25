package com.example.msgestiontrabajos.repository;

import com.example.msgestiontrabajos.entity.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Integer> {

    // Buscar trabajos asociados a una empresa específica
    List<Trabajo> findByEmpresaId(Integer empresaId);

    // Buscar trabajos por estado (ACTIVO o INACTIVO)
    List<Trabajo> findByEstado(Trabajo.Estado estado);

    // Buscar trabajos por empresa y estado
    List<Trabajo> findByEmpresaIdAndEstado(Integer empresaId, Trabajo.Estado estado);
}
