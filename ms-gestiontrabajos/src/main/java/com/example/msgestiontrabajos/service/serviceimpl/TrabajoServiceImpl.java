package com.example.msgestiontrabajos.service.serviceimpl;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.feign.EmpresaFeign;
import com.example.msgestiontrabajos.repository.TrabajoRepository;
import com.example.msgestiontrabajos.service.EmailService;
import com.example.msgestiontrabajos.service.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TrabajoServiceImpl implements TrabajoService {

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmpresaFeign empresaFeign;

    @Override
    public List<Trabajo> list() {
        List<Trabajo> trabajos = trabajoRepository.findByEstado(Trabajo.Estado.ACTIVO);
        trabajos.forEach(this::asignarDetallesDeEmpresa);
        return trabajos;
    }

    @Override
    public Optional<Trabajo> getById(Integer id) {
        return trabajoRepository.findById(id);
    }

    @Override
    public Trabajo save(Trabajo trabajo) {
        trabajo.setFechaPublicacion(LocalDateTime.now());
        trabajo.setEstado(Trabajo.Estado.ACTIVO);

        Trabajo savedTrabajo = trabajoRepository.save(trabajo);

        if (trabajo.getEmpresaId() != null) {
            asignarDetallesDeEmpresa(savedTrabajo);
        }

        enviarNotificacion(savedTrabajo);

        return savedTrabajo;
    }

    @Override
    public Trabajo update(Trabajo trabajo) {
        if (!trabajoRepository.existsById(trabajo.getId())) {
            throw new IllegalArgumentException("Trabajo no encontrado con ID: " + trabajo.getId());
        }
        return trabajoRepository.save(trabajo);
    }

    @Override
    public void delete(Integer id) {
        if (!trabajoRepository.existsById(id)) {
            throw new IllegalArgumentException("Trabajo no encontrado con ID: " + id);
        }
        trabajoRepository.deleteById(id);
    }

    @Override
    public List<Trabajo> findByEmpresaId(Integer empresaId) {
        return trabajoRepository.findByEmpresaIdAndEstado(empresaId, Trabajo.Estado.ACTIVO);
    }

    @Override
    public EmpresaDto obtenerTrabajoConEmpresa(Integer id) {
        // Buscar el trabajo por ID en la base de datos
        Trabajo trabajo = trabajoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trabajo no encontrado con ID: " + id));

        // Llamar al microservicio de empresa usando Feign
        EmpresaDto empresaDto = empresaFeign.getById(trabajo.getEmpresaId());

        // Verificar que la respuesta no sea nula
        if (empresaDto != null) {
            return empresaDto;
        }

        // Lanzar excepción en caso de error
        throw new RuntimeException("Error al obtener detalles de la empresa.");
    }


    @Scheduled(cron = "0 0 0 * * *") // Ejecución diaria a medianoche
    @Transactional
    public void actualizarEstadoTrabajos() {
        List<Trabajo> trabajos = trabajoRepository.findByEstado(Trabajo.Estado.ACTIVO);
        LocalDateTime ahora = LocalDateTime.now();

        for (Trabajo trabajo : trabajos) {
            if (trabajo.getFechaFin() != null && trabajo.getFechaFin().isBefore(ahora)) {
                trabajo.setEstado(Trabajo.Estado.INACTIVO);
                trabajoRepository.save(trabajo);
            }
        }
    }

    // Método privado para asignar detalles de la empresa a un trabajo
    private void asignarDetallesDeEmpresa(Trabajo trabajo) {
        if (trabajo.getEmpresaId() == null) {
            System.err.println("El trabajo con ID " + trabajo.getId() + " no tiene empresa asociada.");
            trabajo.setEmpresaDto(null);
            return;
        }
        // Realiza la llamada Feign con manejo de excepciones
        try {
            EmpresaDto empresaDto = empresaFeign.getById(trabajo.getEmpresaId());
            trabajo.setEmpresaDto(empresaDto);
        } catch (feign.FeignException.NotFound e) {
            System.err.println("Empresa no encontrada para el ID: " + trabajo.getEmpresaId());
            trabajo.setEmpresaDto(null);
        }
    }




    // Método privado para enviar notificaciones por correo
    private void enviarNotificacion(Trabajo trabajo) {
        try {
            String empresaEmail = obtenerCorreoDeEmpresa(trabajo.getEmpresaId());
            String subject = "Nuevo trabajo creado: " + trabajo.getTitulo();
            String text = construirContenidoEmail(trabajo);

            emailService.sendEmail(empresaEmail, subject, text);
        } catch (Exception e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    private String obtenerCorreoDeEmpresa(Integer empresaId) {
        EmpresaDto empresaDto = empresaFeign.getById(empresaId); // Llamada Feign devuelve EmpresaDto directamente
        if (empresaDto != null) {
            return empresaDto.getCorreo(); // Devolver el correo de la empresa
        }
        throw new RuntimeException("No se pudo obtener el correo de la empresa con ID: " + empresaId);
    }


    private String construirContenidoEmail(Trabajo trabajo) {
        String logoUrl = "https://example.com/logo.png";
        return "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<div style='text-align: center; margin-bottom: 20px;'>" +
                "<img src='" + logoUrl + "' alt='Logo de la Empresa' style='width: 100px; height: auto;'/>" +
                "</div>" +
                "<h2>Estimado Cliente,</h2>" +
                "<p>Se ha creado un nuevo trabajo titulado <strong>" + trabajo.getTitulo() + "</strong>.</p>" +
                "<p>Detalles del trabajo:</p>" +
                "<ul>" +
                "<li><strong>Descripción:</strong> " + trabajo.getDescripcion() + "</li>" +
                "<li><strong>Ubicación:</strong> " + trabajo.getUbicacion() + "</li>" +
                "<li><strong>Tipo de Contrato:</strong> " + trabajo.getTipoContrato() + "</li>" +
                "<li><strong>Salario:</strong> " + trabajo.getSalario() + "</li>" +
                "<li><strong>Fecha de Publicación:</strong> " + trabajo.getFechaPublicacion() + "</li>" +
                "</ul>" +
                "<p>Saludos cordiales,</p>" +
                "<p><strong>Equipo de Gestión de Trabajos</strong></p>" +
                "</body>" +
                "</html>";
    }
}
