package com.example.mspostulaciones.controller;

import com.example.mspostulaciones.entity.VideoConferencia;
import com.example.mspostulaciones.service.VideoConferenciaService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/videoconferencias")
public class VideoConferenciaController {

    private final VideoConferenciaService service;

    public VideoConferenciaController(VideoConferenciaService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public VideoConferencia crearSala(@RequestParam String empresaId, @RequestParam String candidatoId) {
        return service.crearSala(empresaId, candidatoId);
    }

    @GetMapping("/{roomId}")
    public Optional<VideoConferencia> obtenerSala(@PathVariable String roomId) {
        return service.obtenerSalaPorRoomId(roomId);
    }

    @PostMapping("/finalizar/{roomId}")
    public void finalizarSala(@PathVariable String roomId) {
        service.finalizarSala(roomId);
    }
}
