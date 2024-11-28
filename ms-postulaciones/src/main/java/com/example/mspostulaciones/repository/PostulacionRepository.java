package com.example.mspostulaciones.repository;

import com.example.mspostulaciones.entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {

    List<Postulacion> findByTrabajoId(Integer trabajoId);

    long countByTrabajoId(Integer trabajoId);

    Optional<Postulacion> findById(Integer id);


}
