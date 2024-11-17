package com.example.mspostulaciones.service.serviceimpl;

import com.example.mspostulaciones.entity.VideoConferencia;
import com.example.mspostulaciones.repository.VideoConferenciaRepository;
import com.example.mspostulaciones.service.VideoConferenciaService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class VideoConferenciaServiceImpl implements VideoConferenciaService {

    private final VideoConferenciaRepository repository;

    public VideoConferenciaServiceImpl(VideoConferenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    public VideoConferencia crearSala(String empresaId, String candidatoId) {
        VideoConferencia conferencia = new VideoConferencia();
        conferencia.setRoomId(UUID.randomUUID().toString());
        conferencia.setEmpresaId(empresaId);
        conferencia.setCandidatoId(candidatoId);
        conferencia.setFechaInicio(LocalDateTime.now());
        conferencia.setStatus("pendiente");
        return repository.save(conferencia);
    }

    @Override
    public Optional<VideoConferencia> obtenerSalaPorRoomId(String roomId) {
        return repository.findByRoomId(roomId);
    }

    @Override
    public void finalizarSala(String roomId) {
        Optional<VideoConferencia> conferenciaOpt = repository.findByRoomId(roomId);
        conferenciaOpt.ifPresent(conferencia -> {
            conferencia.setFechaFin(LocalDateTime.now());
            conferencia.setStatus("finalizada");
            repository.save(conferencia);
        });
    }
}
