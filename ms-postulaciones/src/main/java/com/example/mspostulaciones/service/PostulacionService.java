package com.example.mspostulaciones.service;

import com.example.mspostulaciones.dto.PostulacionDto;
import com.example.mspostulaciones.entity.Postulacion;

import java.util.List;
import java.util.Optional;

public interface PostulacionService {
    Optional<PostulacionDto> obtenerPostulacionPorId(Integer id);

    List<Postulacion> listar();

    Optional<Postulacion> listarPorId(Integer id);

    Postulacion guardar(Postulacion postulacion);

    Postulacion actualizar(Postulacion postulacion);

    void eliminar(Integer id);

    Postulacion actualizarEstado(Integer id, String estado, String comentario);

    // Declaración del método que cuenta las postulaciones por trabajo
    long countByTrabajoId(Integer trabajoId);
}