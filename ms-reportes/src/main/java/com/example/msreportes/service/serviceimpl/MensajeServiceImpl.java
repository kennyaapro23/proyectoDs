package com.example.msreportes.service.serviceimpl;

import com.example.msreportes.dto.CandidatoDto;
import com.example.msreportes.dto.EmpresaDto;
import com.example.msreportes.entity.Conversacion;
import com.example.msreportes.entity.Mensaje;
import com.example.msreportes.feign.EmpresaFeign;
import com.example.msreportes.feign.GestionCandidatosFeign;
import com.example.msreportes.repository.ConversacionRepository;
import com.example.msreportes.repository.MensajeRepository;
import com.example.msreportes.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private ConversacionRepository conversacionRepository;

    @Autowired
    private EmpresaFeign empresaFeign;

    @Autowired
    private GestionCandidatosFeign gestionCandidatosFeign;

    @Override
    public Mensaje enviarMensaje(Mensaje mensaje) {
        Long conversacionId = obtenerConversacionIdExistente(mensaje.getRemitenteId(), mensaje.getDestinatarioId());

        if (conversacionId != null) {
            mensaje.setConversacionId(conversacionId);
        } else {
            conversacionId = crearNuevaConversacion(mensaje.getRemitenteId(), mensaje.getDestinatarioId(), mensaje.getTipoRemitente(), mensaje.getTipoDestinatario());
            mensaje.setConversacionId(conversacionId);
        }

        // Obtener nombres del remitente y destinatario
        if ("EMPRESA".equals(mensaje.getTipoRemitente())) {
            EmpresaDto empresa = empresaFeign.getById(mensaje.getRemitenteId().intValue()).getBody();
            mensaje.setRemitenteNombre(empresa != null ? empresa.getNombreEmpresa() : "Desconocido");
        } else if ("CANDIDATO".equals(mensaje.getTipoRemitente())) {
            CandidatoDto candidato = gestionCandidatosFeign.obtenerCandidatoPorId(mensaje.getRemitenteId().intValue());
            mensaje.setRemitenteNombre(candidato != null ? candidato.getNombreCompleto() : "Desconocido");
        }

        if ("EMPRESA".equals(mensaje.getTipoDestinatario())) {
            EmpresaDto empresa = empresaFeign.getById(mensaje.getDestinatarioId().intValue()).getBody();
            mensaje.setDestinatarioNombre(empresa != null ? empresa.getNombreEmpresa() : "Desconocido");
        } else if ("CANDIDATO".equals(mensaje.getTipoDestinatario())) {
            CandidatoDto candidato = gestionCandidatosFeign.obtenerCandidatoPorId(mensaje.getDestinatarioId().intValue());
            mensaje.setDestinatarioNombre(candidato != null ? candidato.getNombreCompleto() : "Desconocido");
        }

        mensaje.setFechaEnvio(new Date());
        return mensajeRepository.save(mensaje);
    }

    private Long obtenerConversacionIdExistente(Long remitenteId, Long destinatarioId) {
        return conversacionRepository.findByRemitenteIdAndDestinatarioId(remitenteId, destinatarioId)
                .map(Conversacion::getId)
                .orElse(null);
    }

    private Long crearNuevaConversacion(Long remitenteId, Long destinatarioId, String tipoRemitente, String tipoDestinatario) {
        Conversacion nuevaConversacion = new Conversacion();
        nuevaConversacion.setRemitenteId(remitenteId);
        nuevaConversacion.setDestinatarioId(destinatarioId);
        nuevaConversacion.setTipoRemitente(tipoRemitente);
        nuevaConversacion.setTipoDestinatario(tipoDestinatario);
        nuevaConversacion.setFechaCreacion(new Date());
        return conversacionRepository.save(nuevaConversacion).getId();
    }

    @Override
    public List<Mensaje> obtenerMensajesNoLeidos(Long destinatarioId) {
        return mensajeRepository.findByDestinatarioIdAndLeidoFalse(destinatarioId);
    }

    @Override
    public void marcarComoLeido(Long mensajeId) {
        Mensaje mensaje = mensajeRepository.findById(mensajeId)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));
        mensaje.setLeido(true);
        mensajeRepository.save(mensaje);
    }

    @Override
    public List<Mensaje> obtenerMensajesPorConversacion(Long conversacionId) {
        return mensajeRepository.findByConversacionId(conversacionId);
    }
}
