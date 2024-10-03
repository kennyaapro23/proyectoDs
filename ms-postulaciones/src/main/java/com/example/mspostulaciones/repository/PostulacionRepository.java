package com.example.mspostulaciones.repository;


import com.example.mspostulaciones.entity.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostulacionRepository extends JpaRepository<Postulacion, Integer> {
}
