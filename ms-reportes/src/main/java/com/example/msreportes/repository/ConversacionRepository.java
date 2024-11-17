package com.example.msreportes.repository;

import com.example.msreportes.entity.Conversacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Long> {
    Optional<Conversacion> findByRemitenteIdAndDestinatarioId(Long remitenteId, Long destinatarioId);
}
