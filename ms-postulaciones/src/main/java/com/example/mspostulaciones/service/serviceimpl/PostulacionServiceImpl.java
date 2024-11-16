package com.example.mspostulaciones.service.serviceimpl;

import com.example.mspostulaciones.dto.CandidatoDto;
import com.example.mspostulaciones.dto.PostulacionDto;
import com.example.mspostulaciones.dto.TrabajoDto;
import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.feign.GestionCandidatosFeign;
import com.example.mspostulaciones.feign.TrabajoFeign;
import com.example.mspostulaciones.repository.PostulacionRepository;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.mspostulaciones.service.EmailService;

import java.util.List;
import java.util.Optional;

@Service
public class PostulacionServiceImpl implements PostulacionService {

    private final PostulacionRepository postulacionRepository;

    private final TrabajoFeign trabajoFeign;
    private final GestionCandidatosFeign gestionCandidatosFeign;

    @Autowired
    public PostulacionServiceImpl(PostulacionRepository postulacionRepository,

                                  TrabajoFeign trabajoFeign,
                                  GestionCandidatosFeign gestionCandidatosFeign) {
        this.postulacionRepository = postulacionRepository;

        this.trabajoFeign = trabajoFeign;
        this.gestionCandidatosFeign = gestionCandidatosFeign;
    }

    @Override
    public Optional<PostulacionDto> obtenerPostulacionPorId(Integer id) {
        return postulacionRepository.findById(id)
                .map(this::convertToDto)
                .map(dto -> {

                    dto.setTrabajo(fetchTrabajo(dto.getTrabajoId()));
                    dto.setCandidato(fetchCandidato(dto.getCandidatosId()));
                    return dto;
                });
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
        // Guardar la postulación en la base de datos
        Postulacion nuevaPostulacion = postulacionRepository.save(postulacion);

        // Obtener detalles del candidato y trabajo
        CandidatoDto candidato = fetchCandidato(nuevaPostulacion.getCandidatosId());
        TrabajoDto trabajo = fetchTrabajo(nuevaPostulacion.getTrabajoId());

        // Validar si se obtuvieron los datos correctamente antes de enviar los correos
        if (candidato != null && trabajo != null) {
            if (candidato.getCorreoElectronico() != null && !candidato.getCorreoElectronico().isEmpty()) {
                enviarCorreoCandidato(candidato, trabajo);
            } else {
                System.err.println("El candidato no tiene un correo electrónico válido.");
            }

            if (trabajo.getEmpresaCorreo() != null && !trabajo.getEmpresaCorreo().isEmpty()) {
                enviarCorreoEmpresa(candidato, trabajo);
            } else {
                System.err.println("La empresa no tiene un correo electrónico válido.");
            }
        } else {
            System.err.println("No se pudo obtener los datos del candidato o del trabajo.");
        }

        return nuevaPostulacion;
    }


    @Autowired
    private EmailService emailService;

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


    private void enviarCorreoEmpresa(CandidatoDto candidato, TrabajoDto trabajo) {
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
            emailService.sendEmail(trabajo.getEmpresaCorreo(), asunto, cuerpo);
            System.out.println("Correo enviado a la empresa: " + trabajo.getEmpresaCorreo());
        } catch (Exception e) {
            System.err.println("Error al enviar correo a la empresa: " + e.getMessage());
        }
    }




    @Override
    public Postulacion actualizar(Postulacion postulacion) {
        if (postulacion == null || postulacion.getId() == null) {
            throw new IllegalArgumentException("La postulación o su ID no pueden ser nulos");
        }

        Optional<Postulacion> existente = postulacionRepository.findById(postulacion.getId());
        if (existente.isEmpty()) {
            throw new RuntimeException("Postulación no encontrada para el ID: " + postulacion.getId());
        }

        return postulacionRepository.save(postulacion);
    }

    @Override
    public void eliminar(Integer id) {
        if (postulacionRepository.existsById(id)) {
            postulacionRepository.deleteById(id);
        } else {
            throw new RuntimeException("Postulación no encontrada para el ID: " + id);
        }
    }

    @Override
    public Postulacion actualizarEstado(Integer id, String estado, String comentario) {
        Postulacion postulacion = postulacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada para el ID: " + id));
        postulacion.setEstado(estado);
        postulacion.setComentario(comentario);
        return postulacionRepository.save(postulacion);
    }

    @Override
    public long countByTrabajoId(Integer trabajoId) {
        return postulacionRepository.countByTrabajoId(trabajoId);
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


    private TrabajoDto fetchTrabajo(Integer trabajoId) {
        try {
            return trabajoFeign.obtenerTrabajoPorId(trabajoId);
        } catch (Exception e) {
            System.err.println("Error al obtener el trabajo con ID: " + trabajoId + ". Detalle: " + e.getMessage());
            return null;
        }
    }

    private CandidatoDto fetchCandidato(Integer candidatoId) {
        try {
            return gestionCandidatosFeign.obtenerCandidatoPorId(candidatoId);
        } catch (Exception e) {
            System.err.println("Error al obtener el candidato con ID: " + candidatoId + ". Detalle: " + e.getMessage());
            return null;
        }
    }

    public List<CandidatoDto> listarPostulantesPorTrabajoId(Integer trabajoId) {
        List<Postulacion> postulaciones = postulacionRepository.findByTrabajoId(trabajoId);

        // Convertir las postulaciones en una lista de CandidatoDto
        List<CandidatoDto> candidatos = postulaciones.stream()
                .map(postulacion -> fetchCandidato(postulacion.getCandidatosId()))
                .filter(candidato -> candidato != null)
                .toList();

        return candidatos;
    }


}
