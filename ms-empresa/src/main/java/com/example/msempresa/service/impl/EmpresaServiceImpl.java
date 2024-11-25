package com.example.msempresa.service.impl;

import com.example.msempresa.dto.GestiontrabajosDto;
import com.example.msempresa.dto.AuthUserDto;
import com.example.msempresa.entity.Empresa;
import com.example.msempresa.feign.AuthUserFeign;
import com.example.msempresa.feign.GestiontrabajosFeign;
import com.example.msempresa.repository.EmpresaRepository;
import com.example.msempresa.security.JwtDecoder;
import com.example.msempresa.service.EmpresaService;
import com.example.msempresa.service.EmailService;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private static final Logger logger = LoggerFactory.getLogger(EmpresaServiceImpl.class);

    private final GestiontrabajosFeign trabajosFeign;
    private final EmpresaRepository empresaRepository;
    private final EmailService emailService;
    private final JwtDecoder jwtDecoder;
    private final AuthUserFeign authUserFeign; // Inyección del feign para obtener detalles del usuario

    @Autowired
    public EmpresaServiceImpl(GestiontrabajosFeign trabajosFeign,
                              EmpresaRepository empresaRepository,
                              EmailService emailService,
                              JwtDecoder jwtDecoder,
                              AuthUserFeign authUserFeign) {
        this.trabajosFeign = trabajosFeign;
        this.empresaRepository = empresaRepository;
        this.emailService = emailService;
        this.jwtDecoder = jwtDecoder;
        this.authUserFeign = authUserFeign;
    }

    @Override
    public GestiontrabajosDto publicarTrabajoDesdeEmpresa(GestiontrabajosDto trabajoDto, Integer empresaId) {
        try {
            trabajoDto.setEmpresaId(empresaId);
            ResponseEntity<GestiontrabajosDto> response = trabajosFeign.publicarTrabajo(trabajoDto);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Trabajo publicado exitosamente: {}", response.getBody());
                return response.getBody();
            } else {
                logger.error("Error al publicar trabajo: {}", response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            logger.error("Error al comunicar con el servicio de trabajos: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Empresa> list() {
        try {
            List<Empresa> empresas = empresaRepository.findAll();
            // Poblar el authUserDto para cada empresa
            for (Empresa empresa : empresas) {
                AuthUserDto authUserDto = authUserFeign.getById(empresa.getUsuarioId()).getBody();
                empresa.setAuthUserDto(authUserDto);  // Poblando el campo transitorio
            }
            return empresas;
        } catch (Exception e) {
            logger.error("Error al listar las empresas", e);
            throw new RuntimeException("Error al listar las empresas", e);
        }
    }

    @Override
    public Optional<Empresa> getById(Integer id) {
        try {
            return empresaRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error al obtener empresa con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al obtener la empresa", e);
        }
    }

    @Override
    public Empresa save(Empresa empresa, Integer userId) {
        try {
            if (empresa.getNombreEmpresa() == null || empresa.getCorreo() == null) {
                throw new IllegalArgumentException("El nombre de la empresa y el correo son obligatorios");
            }

            empresa.setUsuarioId(userId);
            Empresa savedEmpresa = empresaRepository.save(empresa);

            // Enviar correo si se proporciona
            if (savedEmpresa.getCorreo() != null && !savedEmpresa.getCorreo().isEmpty()) {
                try {
                    emailService.sendEmail(
                            savedEmpresa.getCorreo(),
                            "Nueva Empresa Registrada",
                            "<h1>Bienvenido</h1><p>Gracias por registrar tu empresa en nuestra plataforma.</p>"
                    );
                    logger.info("Correo enviado exitosamente a {}", savedEmpresa.getCorreo());
                } catch (MessagingException e) {
                    logger.warn("Error al enviar correo a {}: {}", savedEmpresa.getCorreo(), e.getMessage(), e);
                }
            }

            logger.info("Empresa guardada exitosamente: {}", savedEmpresa);
            return savedEmpresa;
        } catch (Exception e) {
            logger.error("Error al guardar la empresa: {}", e.getMessage(), e);
            throw new RuntimeException("Error al guardar la empresa", e);
        }
    }

    @Override
    public Empresa update(Integer id, Empresa empresa) {
        try {
            if (!empresaRepository.existsById(id)) {
                throw new RuntimeException("La empresa con ID " + id + " no existe");
            }

            empresa.setId(id);
            Empresa updatedEmpresa = empresaRepository.save(empresa);
            logger.info("Empresa actualizada exitosamente: {}", updatedEmpresa);
            return updatedEmpresa;
        } catch (Exception e) {
            logger.error("Error al actualizar la empresa con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al actualizar la empresa", e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            if (!empresaRepository.existsById(id)) {
                throw new RuntimeException("La empresa con ID " + id + " no existe");
            }

            empresaRepository.deleteById(id);
            logger.info("Empresa con ID {} eliminada exitosamente", id);
        } catch (Exception e) {
            logger.error("Error al eliminar la empresa con ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Error al eliminar la empresa", e);
        }
    }

    /**
     * Extrae el ID del usuario desde el token JWT.
     *
     * @param token el token JWT.
     * @return el ID del usuario o null si no se puede extraer.
     */
    public Integer extractUserIdFromToken(String token) {
        try {
            return jwtDecoder.extractUserIdFromToken(token); // Utiliza JwtDecoder para obtener el ID
        } catch (Exception e) {
            logger.error("Error al extraer el ID del usuario desde el token: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Obtiene las empresas asociadas a un usuario por su ID.
     *
     * @param userId ID del usuario.
     * @return Lista de empresas asociadas al usuario.
     */
    public List<Empresa> getEmpresasByUserId(Integer userId) {
        try {
            List<Empresa> empresas = empresaRepository.findByUsuarioId(userId);
            // Rellenar el AuthUserDto para cada empresa
            for (Empresa empresa : empresas) {
                AuthUserDto authUserDto = authUserFeign.getById(empresa.getUsuarioId()).getBody();
                empresa.setAuthUserDto(authUserDto); // Asociamos el AuthUserDto con la empresa
            }
            return empresas;
        } catch (Exception e) {
            logger.error("Error al obtener empresas del usuario con ID {}: {}", userId, e.getMessage(), e);
            throw new RuntimeException("Error al obtener empresas del usuario", e);
        }
    }
}
