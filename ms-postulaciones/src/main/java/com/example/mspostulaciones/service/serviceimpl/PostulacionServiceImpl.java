package com.example.mspostulaciones.service.serviceimpl;


import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.repository.PostulacionRepository;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    @Autowired
    private PostulacionRepository postulacionRepository;

    @Override
    public List<Postulacion> listar() {
        return postulacionRepository.findAll();
    }

    @Override
    public Optional<Postulacion> listarPorId(Integer id) {
        return postulacionRepository.findById(id);
    }

    @Override
    public Postulacion guardar(Postulacion postulacion) {
        postulacion.setFechaPostulacion(LocalDate.now().toString());
        postulacion.setEstado("Pendiente");
        return postulacionRepository.save(postulacion);
    }

    @Override
    public Postulacion actualizar(Postulacion postulacion) {
        return postulacionRepository.save(postulacion);
    }

    @Override
    public void eliminar(Integer id) {
        postulacionRepository.deleteById(id);
    }

    @Override
    public List<Postulacion> listarPorUsuarioId(Integer usuarioId) {
        return postulacionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Postulacion> listarPorTrabajoId(Integer trabajoId) {
        return postulacionRepository.findByTrabajoId(trabajoId);
    }

    @Override
    public Postulacion actualizarEstado(Integer id, String estado, String comentario) {
        Optional<Postulacion> optionalPostulacion = postulacionRepository.findById(id);
        if (optionalPostulacion.isPresent()) {
            Postulacion postulacion = optionalPostulacion.get();
            postulacion.setEstado(estado);
            postulacion.setComentario(comentario);
            return postulacionRepository.save(postulacion);
        }
        throw new RuntimeException("Postulación no encontrada");
    }
}
