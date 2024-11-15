package com.example.msgestiontrabajos.service.serviceimpl;

import com.example.msgestiontrabajos.dto.EmpresaDto;
import com.example.msgestiontrabajos.entity.Trabajo;
import com.example.msgestiontrabajos.feign.TrabajoFeign;
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
        trabajo.setFechaPublicacion(LocalDateTime.now()); // Asignar la fecha y hora actuales a la fecha de publicación
        trabajo.setEstado(Trabajo.Estado.ACTIVO); // Establecer el estado inicial como ACTIVO
        return trabajoRepository.save(trabajo);
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

    /**
     * Método para obtener el correo de la empresa usando TrabajoFeign
     * @param empresaId ID de la empresa
     * @return Correo de la empresa o un correo predeterminado en caso de error
     */
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


}
