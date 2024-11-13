package com.example.mspostulaciones.service;



import com.example.mspostulaciones.entity.Postulacion;


import java.util.List;
import java.util.Optional;

public interface PostulacionService {
    List<Postulacion> listar();
    Optional<Postulacion> listarPorId(Integer id);
    Postulacion guardar(Postulacion postulacion);
    Postulacion actualizar(Postulacion postulacion);
    void eliminar(Integer id);
    List<Postulacion> listarPorUsuarioId(Integer usuarioId);
    List<Postulacion> listarPorTrabajoId(Integer trabajoId);

    Postulacion actualizarEstado(Integer id, String estado, String comentario);
}
