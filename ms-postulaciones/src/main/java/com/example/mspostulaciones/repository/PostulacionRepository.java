package com.example.mspostulaciones.repository;


import com.example.mspostulaciones.entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {
    List<Postulacion> findByUsuarioId(Integer usuarioId);
    List<Postulacion> findByTrabajoId(Integer trabajoId);
}
