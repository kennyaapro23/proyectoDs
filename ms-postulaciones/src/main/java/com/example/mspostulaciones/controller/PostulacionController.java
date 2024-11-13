package com.example.mspostulaciones.controller;

import com.example.mspostulaciones.dto.PostulacionDto;
import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postulacion")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    // Endpoint para listar todas las postulaciones
    @GetMapping
    public ResponseEntity<List<Postulacion>> listarTodas() {
        List<Postulacion> postulaciones = postulacionService.listar();
        return (postulaciones.isEmpty()) ?
                ResponseEntity.noContent().build() : ResponseEntity.ok(postulaciones);
    }

    // Endpoint para obtener una postulación por ID con todos los detalles (usuario, trabajo, candidato)
    @GetMapping("/{id}")
    public ResponseEntity<PostulacionDto> obtenerPorId(@PathVariable Integer id) {
        try {
            Optional<PostulacionDto> postulacionDto = postulacionService.obtenerPostulacionPorId(id);
            return postulacionDto.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para crear una nueva postulación
    @PostMapping
    public ResponseEntity<Postulacion> crear(@RequestBody Postulacion postulacion) {
        try {
            Postulacion nuevaPostulacion = postulacionService.guardar(postulacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPostulacion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Endpoint para actualizar una postulación
    @PutMapping("/{id}")
    public ResponseEntity<Postulacion> actualizar(@PathVariable Integer id, @RequestBody Postulacion postulacion) {
        if (!postulacionService.listarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            postulacion.setId(id);
            Postulacion actualizada = postulacionService.actualizar(postulacion);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint para eliminar una postulación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!postulacionService.listarPorId(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            postulacionService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint para actualizar el estado de una postulación
    @PutMapping("/{id}/estado")
    public ResponseEntity<Postulacion> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam(value = "estado") String estado,
            @RequestParam(value = "comentario", required = false) String comentario) {

        if (estado == null || estado.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Optional<Postulacion> postulacionExistente = postulacionService.listarPorId(id);
        if (!postulacionExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        try {
            Postulacion actualizada = postulacionService.actualizarEstado(id, estado, comentario);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Endpoint adicional para contar las postulaciones de un trabajo
    @GetMapping("/trabajo/{trabajoId}/count")
    public ResponseEntity<Long> contarPostulacionesPorTrabajo(@PathVariable Integer trabajoId) {
        try {
            long count = postulacionService.countByTrabajoId(trabajoId);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
