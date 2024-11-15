package com.example.msgestiontrabajos.repository;

import com.example.msgestiontrabajos.entity.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Integer> {
    // Método para encontrar trabajos asociados a una empresa específica
    List<Trabajo> findByEmpresaId(Integer empresaId);

    // Método para encontrar trabajos por estado (ACTIVO o INACTIVO)
    List<Trabajo> findByEstado(Trabajo.Estado estado);
}
