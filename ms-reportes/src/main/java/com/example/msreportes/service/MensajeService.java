package com.example.msreportes.service;

import com.example.msreportes.entity.Mensaje;

import java.util.List;

public interface MensajeService {
    Mensaje enviarMensaje(Mensaje mensaje);
    List<Mensaje> obtenerMensajesNoLeidos(Long destinatarioId);
    void marcarComoLeido(Long mensajeId);
    List<Mensaje> obtenerMensajesPorConversacion(Long conversacionId);
}
