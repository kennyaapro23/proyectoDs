package com.example.mspostulaciones.service;

import com.example.mspostulaciones.entity.VideoConferencia;

import java.util.Optional;

public interface VideoConferenciaService {
    VideoConferencia crearSala(String empresaId, String candidatoId);
    Optional<VideoConferencia> obtenerSalaPorRoomId(String roomId);
    void finalizarSala(String roomId);
}
