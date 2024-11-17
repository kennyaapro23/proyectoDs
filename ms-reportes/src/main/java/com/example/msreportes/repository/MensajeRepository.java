package com.example.msreportes.repository;

import com.example.msreportes.entity.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByDestinatarioIdAndLeidoFalse(Long destinatarioId);
    List<Mensaje> findByConversacionId(Long conversacionId); // Método para obtener mensajes por conversación
}
