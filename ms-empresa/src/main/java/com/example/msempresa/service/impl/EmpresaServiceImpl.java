package com.example.msempresa.service.impl;

import com.example.msempresa.dto.GestiontrabajosDto;
import com.example.msempresa.entity.Empresa;
import com.example.msempresa.feign.GestiontrabajosFeign;
import com.example.msempresa.repository.EmpresaRepository;
import com.example.msempresa.service.EmpresaService;
import com.example.msempresa.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final GestiontrabajosFeign trabajosFeign;
    private final EmpresaRepository empresaRepository;
    private final EmailService emailService;

    @Autowired
    public EmpresaServiceImpl(GestiontrabajosFeign trabajosFeign,
                              EmpresaRepository empresaRepository,
                              EmailService emailService) {
        this.trabajosFeign = trabajosFeign;
        this.empresaRepository = empresaRepository;
        this.emailService = emailService;
    }

    @Override
    public GestiontrabajosDto publicarTrabajoDesdeEmpresa(GestiontrabajosDto trabajoDto, Integer empresaId) {
        try {
            // Ahora que el campo existe, esto debería funcionar sin problemas
            trabajoDto.setEmpresaId(empresaId);

            ResponseEntity<GestiontrabajosDto> response = trabajosFeign.publicarTrabajo(trabajoDto);

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                System.out.println("Error al publicar trabajo: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error al comunicar con el servicio de trabajos: " + e.getMessage());
            return null;
        }
    }


    @Override
    public List<Empresa> list() {
        return empresaRepository.findAll();
    }

    @Override
    public Optional<Empresa> getById(Integer id) {
        return empresaRepository.findById(id);
    }

    @Override
    public Empresa save(Empresa empresa) {
        Empresa savedEmpresa = empresaRepository.save(empresa);

        // Enviar un correo al crear una nueva empresa
        if (savedEmpresa.getCorreo() != null && !savedEmpresa.getCorreo().isEmpty()) {
            try {
                emailService.sendEmail(
                        savedEmpresa.getCorreo(),
                        "Nueva Empresa Registrada",
                        "<h1>Bienvenido</h1><p>Gracias por registrar tu empresa en nuestra plataforma.</p>"
                );
            } catch (MessagingException e) {
                System.err.println("Error al enviar correo a " + savedEmpresa.getCorreo() + ": " + e.getMessage());
            }
        }

        return savedEmpresa;
    }

    @Override
    public Empresa update(Integer id, Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @Override
    public void delete(Integer id) {
        empresaRepository.deleteById(id);
    }
}
