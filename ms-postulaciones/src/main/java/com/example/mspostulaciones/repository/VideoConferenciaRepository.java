package com.example.mspostulaciones.repository;

import com.example.mspostulaciones.entity.VideoConferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoConferenciaRepository extends JpaRepository<VideoConferencia, Long> {
    Optional<VideoConferencia> findByRoomId(String roomId);
}
