package com.example.msgestiontrabajos.service.serviceimpl;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.dto.TrabajoDto;

import com.example.msgestiontrabajos.feign.TrabajoFeign;
import com.example.msgestiontrabajos.repository.TrabajoRepository;
import com.example.msgestiontrabajos.service.EmailService;
import com.example.msgestiontrabajos.service.TrabajoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
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
    private TrabajoFeign trabajoFeign;

    @Override
    public List<Trabajo> list() {
        return trabajoRepository.findByEstado(Trabajo.Estado.ACTIVO);
    }


    @Override
    public Optional<Trabajo> getById(Integer id) {
        return trabajoRepository.findById(id);
    }

    @Override
    public Trabajo save(Trabajo trabajo) {
        // Asignar la fecha y estado por defecto al trabajo
        trabajo.setFechaPublicacion(LocalDateTime.now());
        trabajo.setEstado(Trabajo.Estado.ACTIVO);

        // Guardar el trabajo en la base de datos
        Trabajo savedTrabajo = trabajoRepository.save(trabajo);

        // Obtener los detalles de la empresa usando Feign
        if (trabajo.getEmpresaId() != null) {
            ResponseEntity<EmpresaDto> response = trabajoFeign.getById(trabajo.getEmpresaId());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                EmpresaDto empresa = response.getBody();
                savedTrabajo.setEmpresaDto(empresa); // Asignar los detalles de la empresa al trabajo
            }
        }

        // Obtener el correo de la empresa para el envío de notificaciones
        String empresaEmail = obtenerCorreoDeEmpresa(trabajo.getEmpresaId());

        // Configurar el asunto y el contenido del correo
        String subject = "Nuevo trabajo creado: " + trabajo.getTitulo();
        String logoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQC0WesYBUwIPE8kXYf42rQRFH3FkvLIGIzRQ&s";
        String text = "<html>" +
                "<body style='font-family: Arial, sans-serif;'>" +
                "<div style='text-align: center; margin-bottom: 20px;'>" +
                "<img src='" + logoUrl + "' alt='Logo de la Empresa' style='width: 100px; height: auto;'/>" +
                "</div>" +
                "<h2 style='color: #2e6da4;'>Estimado Cliente,</h2>" +
                "<p>Nos complace informarle que se ha creado un nuevo trabajo titulado <strong>'" + trabajo.getTitulo() + "'</strong> asociado a su empresa.</p>" +
                "<p><strong>Detalles del trabajo:</strong></p>" +
                "<ul>" +
                "<li><strong>Descripción:</strong> " + trabajo.getDescripcion() + "</li>" +
                "<li><strong>Ubicación:</strong> " + trabajo.getUbicacion() + "</li>" +
                "<li><strong>Tipo de Contrato:</strong> " + trabajo.getTipoContrato() + "</li>" +
                "<li><strong>Salario:</strong> " + trabajo.getSalario() + "</li>" +
                "<li><strong>Fecha de Publicación:</strong> " + trabajo.getFechaPublicacion() + "</li>" +
                "</ul>" +
                "<p>Si tiene alguna pregunta o necesita más información, no dude en ponerse en contacto con nosotros.</p>" +
                "<p>Saludos cordiales,</p>" +
                "<p style='color: #2e6da4;'><strong>Su equipo de gestión de trabajos</strong></p>" +
                "</body>" +
                "</html>";

        // Enviar el correo electrónico
        try {
            emailService.sendEmail(empresaEmail, subject, text);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al enviar el correo a " + empresaEmail + ": " + e.getMessage());
        }

        return savedTrabajo;
    }


    @Override
    public Trabajo update(Trabajo trabajo) {
        return trabajoRepository.save(trabajo);
    }

    @Override
    public void delete(Integer id) {
        trabajoRepository.deleteById(id);
    }

    @Override
    public List<Trabajo> findByEmpresaId(Integer empresaId) {
        return trabajoRepository.findByEmpresaId(empresaId);
    }

    /**
     * Cron Job que se ejecuta diariamente a medianoche para actualizar el estado
     * de los trabajos que han alcanzado su fechaFin, cambiándolos a INACTIVO.
     */
    @Scheduled(cron = "0 0 0 * * *") // Se ejecuta a medianoche todos los días
    @Transactional
    public void actualizarEstadoTrabajos() {
        List<Trabajo> trabajos = trabajoRepository.findByEstado(Trabajo.Estado.ACTIVO);
        LocalDateTime ahora = LocalDateTime.now();

        for (Trabajo trabajo : trabajos) {
            if (trabajo.getFechaFin() != null && trabajo.getFechaFin().isBefore(ahora)) {
                trabajo.setEstado(Trabajo.Estado.INACTIVO);
                trabajoRepository.save(trabajo); // Actualizar el estado en la base de datos
            }
        }
    }

    private String obtenerCorreoDeEmpresa(Integer empresaId) {
        ResponseEntity<EmpresaDto> response = trabajoFeign.getById(empresaId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            EmpresaDto empresa = response.getBody();
            return empresa.getCorreo(); // Asegúrate de que `EmpresaDto` tiene un campo `correo`
        } else {
            System.err.println("Error al obtener el correo de la empresa con ID: " + empresaId);
            return "correo@predeterminado.com"; // Valor predeterminado o lanza una excepción si prefieres
        }
    }


    @Override
    public TrabajoDto obtenerTrabajoConEmpresa(Integer id) {
        // Buscar el trabajo por ID
        Optional<Trabajo> optionalTrabajo = trabajoRepository.findById(id);

        if (optionalTrabajo.isPresent()) {
            Trabajo trabajo = optionalTrabajo.get();

            // Usar Feign para obtener los detalles de la empresa
            ResponseEntity<EmpresaDto> response = trabajoFeign.getById(trabajo.getEmpresaId());

            // Crear el DTO de respuesta
            TrabajoDto trabajoDto = new TrabajoDto();
            trabajoDto.setId(trabajo.getId());
            trabajoDto.setTitulo(trabajo.getTitulo());
            trabajoDto.setDescripcion(trabajo.getDescripcion());
            trabajoDto.setUbicacion(trabajo.getUbicacion());
            trabajoDto.setTipoContrato(trabajo.getTipoContrato());
            trabajoDto.setSalario(trabajo.getSalario());
            trabajoDto.setFechaPublicacion(trabajo.getFechaPublicacion());
            trabajoDto.setFechaInicio(trabajo.getFechaInicio());
            trabajoDto.setFechaFin(trabajo.getFechaFin());
            trabajoDto.setEstado(trabajo.getEstado());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                EmpresaDto empresa = response.getBody();
                trabajoDto.setEmpresaNombre(empresa.getNombreEmpresa());
                trabajoDto.setEmpresaCorreo(empresa.getCorreo());
            }
            return trabajoDto;
        } else {
            throw new RuntimeException("Trabajo no encontrado con el ID: " + id);
        }
    }
}
