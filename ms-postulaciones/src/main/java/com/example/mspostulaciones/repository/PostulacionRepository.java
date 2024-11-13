package com.example.mspostulaciones.repository;


import com.example.mspostulaciones.entity.Postulacion;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {
    long countByTrabajoId(Integer trabajoId);
}