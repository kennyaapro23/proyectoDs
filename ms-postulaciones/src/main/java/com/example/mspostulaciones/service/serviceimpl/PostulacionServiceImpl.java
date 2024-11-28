package com.example.mspostulaciones.service.serviceimpl;

import com.example.mspostulaciones.dto.CandidatoDto;
import com.example.mspostulaciones.dto.EmpresaDto;
import com.example.mspostulaciones.dto.PostulacionDto;
import com.example.mspostulaciones.dto.TrabajoDto;
import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.feign.GestionCandidatosFeign;
import com.example.mspostulaciones.feign.TrabajoFeign;
import com.example.mspostulaciones.feign.EmpresaFeign;
import com.example.mspostulaciones.repository.PostulacionRepository;
import com.example.mspostulaciones.service.EmailService;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    private final PostulacionRepository postulacionRepository;
    private final TrabajoFeign trabajoFeign;
    private final GestionCandidatosFeign gestionCandidatosFeign;
    private final EmpresaFeign empresaFeign;
    private final EmailService emailService;

    @Autowired
    public PostulacionServiceImpl(PostulacionRepository postulacionRepository,
                                  TrabajoFeign trabajoFeign,
                                  GestionCandidatosFeign gestionCandidatosFeign,
                                  EmpresaFeign empresaFeign, EmailService emailService) {
        this.postulacionRepository = postulacionRepository;
        this.trabajoFeign = trabajoFeign;
        this.gestionCandidatosFeign = gestionCandidatosFeign;
        this.empresaFeign = empresaFeign;
        this.emailService = emailService;
    }

    @Override
    public Optional<PostulacionDto> obtenerPostulacionPorId(Integer id) {
        return postulacionRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> {
                    dto.setTrabajo(fetchTrabajo(dto.getTrabajoId()));
                    dto.setCandidato(fetchCandidato(dto.getCandidatosId()));
                    if (dto.getTrabajo() != null && dto.getTrabajo().getEmpresaId() != null) {
                        dto.setEmpresa(fetchEmpresa(dto.getTrabajo().getEmpresaId()));
                    }
                    return dto;
                });
    }
    @Override
    public Postulacion actualizar(Postulacion postulacion) {
        // Verificar si la postulación existe en la base de datos
        Optional<Postulacion> postulacionExistente = postulacionRepository.findById(postulacion.getId());
        if (postulacionExistente.isPresent()) {
            // Si existe, actualizamos los datos
            Postulacion postulacionActualizada = postulacionExistente.get();
            postulacionActualizada.setEstado(postulacion.getEstado());
            postulacionActualizada.setComentario(postulacion.getComentario());
            // Otros campos que desees actualizar...

            // Guardamos la postulación actualizada
            return postulacionRepository.save(postulacionActualizada);
        } else {
            // Si no existe, lanzamos una excepción o retornamos null
            throw new RuntimeException("Postulación no encontrada con el ID: " + postulacion.getId());
        }
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
    public List<Postulacion> findByTrabajoId(Integer trabajoId) {
        return postulacionRepository.findByTrabajoId(trabajoId);
    }

    @Override
    public Postulacion guardar(Postulacion postulacion) {
        Postulacion nuevaPostulacion = postulacionRepository.save(postulacion);

        CandidatoDto candidato = fetchCandidato(nuevaPostulacion.getCandidatosId());
        TrabajoDto trabajo = fetchTrabajo(nuevaPostulacion.getTrabajoId());
        EmpresaDto empresa = null;
        if (trabajo != null && trabajo.getEmpresaId() != null) {
            empresa = fetchEmpresa(trabajo.getEmpresaId());
        }

        if (candidato != null && trabajo != null && empresa != null) {
            if (candidato.getCorreoElectronico() != null && !candidato.getCorreoElectronico().isEmpty()) {
                enviarCorreoCandidato(candidato, trabajo);
            }
            if (trabajo.getEmpresaCorreo() != null && !trabajo.getEmpresaCorreo().isEmpty()) {
                enviarCorreoEmpresa(candidato, trabajo, empresa);
            }
        }

        return nuevaPostulacion;
    }

    @Override
    public long countByTrabajoId(Integer trabajoId) {
        return postulacionRepository.countByTrabajoId(trabajoId);
    }

    @Override
    public Postulacion actualizarEstado(Integer id, String estado, String comentario) {
        Optional<Postulacion> postulacionOptional = postulacionRepository.findById(id);
        if (postulacionOptional.isPresent()) {
            Postulacion postulacion = postulacionOptional.get();
            postulacion.setEstado(estado);
            postulacion.setComentario(comentario);
            return postulacionRepository.save(postulacion);
        } else {
            throw new RuntimeException("Postulación no encontrada con ID: " + id);
        }
    }

    @Override
    public void eliminar(Integer id) {
        Optional<Postulacion> postulacionOptional = postulacionRepository.findById(id);
        if (postulacionOptional.isPresent()) {
            postulacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Postulación no encontrada con ID: " + id);
        }
    }

    @Override
    public List<CandidatoDto> listarPostulantesPorTrabajoId(Integer trabajoId) {
        List<Postulacion> postulaciones = postulacionRepository.findByTrabajoId(trabajoId);
        return postulaciones.stream()
                .map(postulacion -> fetchCandidato(postulacion.getCandidatosId()))
                .filter(candidato -> candidato != null)
                .collect(Collectors.toList());
    }

    private CandidatoDto fetchCandidato(Integer candidatoId) {
        try {
            return gestionCandidatosFeign.obtenerCandidatoPorId(candidatoId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el candidato con ID: " + candidatoId, e);
        }
    }

    private TrabajoDto fetchTrabajo(Integer trabajoId) {
        try {
            return trabajoFeign.obtenerTrabajoPorId(trabajoId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el trabajo con ID: " + trabajoId, e);
        }
    }

    private EmpresaDto fetchEmpresa(Integer empresaId) {
        try {
            return empresaFeign.obtenerEmpresaPorId(empresaId);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la empresa con ID: " + empresaId, e);
        }
    }

    private PostulacionDto convertToDto(Postulacion postulacion) {
        PostulacionDto dto = new PostulacionDto();
        dto.setId(postulacion.getId());
        dto.setCandidatosId(postulacion.getCandidatosId());
        dto.setTrabajoId(postulacion.getTrabajoId());
        dto.setFechaPostulacion(postulacion.getFechaPostulacion());
        dto.setEstado(postulacion.getEstado());
        dto.setComentario(postulacion.getComentario());
        return dto;
    }

    private void enviarCorreoCandidato(CandidatoDto candidato, TrabajoDto trabajo) {
        String asunto = "¡Postulación Exitosa!";
        String cuerpo = "<h2>Estimado " + candidato.getNombreCompleto() + "</h2>" +
                "<p>Su postulación al puesto <strong>" + trabajo.getTitulo() + "</strong> ha sido recibida exitosamente.</p>" +
                "<p>Su postulación está en espera de aprobación para pasar a la entrevista personal.</p>" +
                "<p>Empresa: " + trabajo.getEmpresaNombre() + "</p>" +
                "<p>Le deseamos mucho éxito en el proceso de selección.</p>";

        try {
            emailService.sendEmail(candidato.getCorreoElectronico(), asunto, cuerpo);
            System.out.println("Correo enviado al candidato: " + candidato.getCorreoElectronico());
        } catch (Exception e) {
            System.err.println("Error al enviar correo al candidato: " + e.getMessage());
        }
    }

    private void enviarCorreoEmpresa(CandidatoDto candidato, TrabajoDto trabajo, EmpresaDto empresa) {
        String asunto = "Nuevo Postulante para el Puesto: " + trabajo.getTitulo();
        String cuerpo = "<h2>Estimado equipo de " + trabajo.getEmpresaNombre() + "</h2>" +
                "<p>Un nuevo candidato ha postulado al puesto <strong>" + trabajo.getTitulo() + "</strong>.</p>" +
                "<p><strong>Datos del candidato:</strong></p>" +
                "<ul>" +
                "<li>Nombre: " + candidato.getNombreCompleto() + "</li>" +
                "<li>Correo: " + candidato.getCorreoElectronico() + "</li>" +
                "<li>Teléfono: " + candidato.getTelefono() + "</li>" +
                "<li>Dirección: " + candidato.getDireccion() + "</li>" +
                "</ul>" +
                "<p>Por favor, revise la postulación para proceder con la evaluación.</p>";

        try {
            emailService.sendEmail(empresa.getCorreo(), asunto, cuerpo);
            System.out.println("Correo enviado a la empresa: " + empresa.getCorreo());
        } catch (Exception e) {
            System.err.println("Error al enviar correo a la empresa: " + e.getMessage());
        }
    }
}
