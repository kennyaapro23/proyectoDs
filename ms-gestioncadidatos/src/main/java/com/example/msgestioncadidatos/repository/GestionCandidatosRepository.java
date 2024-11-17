package com.example.msgestioncadidatos.repository;

import com.example.msgestioncadidatos.entity.GestionCandidatos;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GestionCandidatosRepository extends JpaRepository<GestionCandidatos, Integer> {

    // Método para buscar un candidato por su correo electrónico
    Optional<GestionCandidatos> findByCorreoElectronico(String correoElectronico);
}
