package com.example.mspostulaciones.repository;

import com.example.mspostulaciones.entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {

    // Método existente para contar postulaciones por trabajoId
    long countByTrabajoId(Integer trabajoId);

    // Nuevo método para obtener postulaciones por trabajoId
    List<Postulacion> findByTrabajoId(Integer trabajoId);
}
