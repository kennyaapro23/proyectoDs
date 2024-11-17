package com.example.msreportes.controller;

import com.example.msreportes.entity.Mensaje;
import com.example.msreportes.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mensajes")
public class MensajesController {

    @Autowired
    private MensajeService mensajeService;

    @PostMapping("/enviar")
    public Mensaje enviarMensaje(@RequestBody Mensaje mensaje) {
        return mensajeService.enviarMensaje(mensaje);
    }

    @GetMapping("/conversacion/{conversacionId}")
    public List<Mensaje> obtenerMensajesPorConversacion(@PathVariable Long conversacionId) {
        return mensajeService.obtenerMensajesPorConversacion(conversacionId);
    }

    @PostMapping("/marcar-leido/{id}")
    public void marcarComoLeido(@PathVariable Long id) {
        mensajeService.marcarComoLeido(id);
    }


}
