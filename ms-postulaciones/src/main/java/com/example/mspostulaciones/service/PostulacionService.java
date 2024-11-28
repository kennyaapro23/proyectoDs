package com.example.mspostulaciones.service;

import com.example.mspostulaciones.dto.CandidatoDto;
import com.example.mspostulaciones.dto.PostulacionDto;
import com.example.mspostulaciones.entity.Postulacion;

import java.util.List;
import java.util.Optional;

public interface PostulacionService {

    // Obtener postulacion por ID
    Optional<PostulacionDto> obtenerPostulacionPorId(Integer id);

    // Listar todas las postulaciones
    List<Postulacion> listar();

    // Listar postulaciones por ID
    Optional<Postulacion> listarPorId(Integer id);


    List<Postulacion> findByTrabajoId(Integer trabajoId);

    // Guardar una postulación
    Postulacion guardar(Postulacion postulacion);

    // Actualizar una postulación
    Postulacion actualizar(Postulacion postulacion);

    // Eliminar una postulación por ID
    void eliminar(Integer id);

    // Actualizar el estado de una postulación
    Postulacion actualizarEstado(Integer id, String estado, String comentario);

    // Contar postulaciones por trabajoId
    long countByTrabajoId(Integer trabajoId);

    // Listar postulantes por trabajoId
    List<CandidatoDto> listarPostulantesPorTrabajoId(Integer trabajoId);
}
