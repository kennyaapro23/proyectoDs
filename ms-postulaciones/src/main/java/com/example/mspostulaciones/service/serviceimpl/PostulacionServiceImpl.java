package com.example.mspostulaciones.service.serviceimpl;

import com.example.mspostulaciones.dto.CandidatoDto;
import com.example.mspostulaciones.dto.PostulacionDto;
import com.example.mspostulaciones.dto.UsuarioDto;
import com.example.mspostulaciones.dto.TrabajoDto;
import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.feign.GestionCandidatosFeign;
import com.example.mspostulaciones.feign.UsuarioFeign;
import com.example.mspostulaciones.feign.TrabajoFeign;
import com.example.mspostulaciones.repository.PostulacionRepository;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PostulacionServiceImpl implements PostulacionService {


    @Autowired
    private PostulacionRepository postulacionRepository;


    @Autowired
    private UsuarioFeign usuarioFeign;

    @Autowired
    private TrabajoFeign trabajoFeign;

    @Autowired
    private GestionCandidatosFeign gestionCandidatosFeign;

    @Override
    public Optional<PostulacionDto> obtenerPostulacionPorId(Integer id) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        PostulacionDto dto = convertToDto(postulacion);

        UsuarioDto usuario = usuarioFeign.obtenerUsuarioPorId(postulacion.getUsuarioId());
        dto.setUsuario(usuario);

        TrabajoDto trabajo = trabajoFeign.obtenerTrabajoPorId(postulacion.getTrabajoId());
        dto.setTrabajo(trabajo);

        CandidatoDto candidato = gestionCandidatosFeign.obtenerCandidatoPorId(postulacion.getUsuarioId());
        dto.setCandidato(candidato);

        return Optional.of(dto);
    }

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
    public Postulacion actualizarEstado(Integer id, String estado, String comentario) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));
        postulacion.setEstado(estado);
        postulacion.setComentario(comentario);
        return postulacionRepository.save(postulacion);
    }

    // Implementación del método para contar postulaciones por trabajo
    @Override
    public long countByTrabajoId(Integer trabajoId) {
        return postulacionRepository.countByTrabajoId(trabajoId);
    }

    private PostulacionDto convertToDto(Postulacion postulacion) {
        PostulacionDto dto = new PostulacionDto();
        dto.setId(postulacion.getId());
        dto.setUsuarioId(postulacion.getUsuarioId());
        dto.setTrabajoId(postulacion.getTrabajoId());
        dto.setFechaPostulacion(postulacion.getFechaPostulacion());
        dto.setEstado(postulacion.getEstado());
        dto.setComentario(postulacion.getComentario());
        return dto;
    }
}