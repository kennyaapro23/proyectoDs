package com.example.mspostulaciones.controller;

import com.example.mspostulaciones.dto.CandidatoDto;
import com.example.mspostulaciones.dto.PostulacionDto;
import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/postulacion")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    // Endpoint para listar todas las postulaciones
    @GetMapping
    public ResponseEntity<List<Postulacion>> listarTodas() {
        List<Postulacion> postulaciones = postulacionService.listar();
        return postulaciones.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(postulaciones);
    }

    // Endpoint para obtener una postulación por ID con todos los detalles
    @GetMapping("/{id}")
    public ResponseEntity<PostulacionDto> obtenerPorId(@PathVariable Integer id) {
        return postulacionService.obtenerPostulacionPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada para el ID: " + id));
    }

    // Endpoint para crear una nueva postulación
    @PostMapping
    public ResponseEntity<Postulacion> crear(@RequestBody Postulacion postulacion) {
        Postulacion nuevaPostulacion = postulacionService.guardar(postulacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaPostulacion);
    }

    // Endpoint para actualizar una postulación
    @PutMapping("/{id}")
    public ResponseEntity<Postulacion> actualizar(@PathVariable Integer id, @RequestBody Postulacion postulacion) {
        if (postulacionService.listarPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        postulacion.setId(id);
        Postulacion actualizada = postulacionService.actualizar(postulacion);
        return ResponseEntity.ok(actualizada);
    }

    // Endpoint para eliminar una postulación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (postulacionService.listarPorId(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        postulacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para actualizar el estado de una postulación
    @PutMapping("/{id}/estado")
    public ResponseEntity<Postulacion> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado,
            @RequestParam(required = false) String comentario) {

        if (estado == null || estado.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Postulacion actualizada = postulacionService.actualizarEstado(id, estado, comentario);
        return ResponseEntity.ok(actualizada);
    }

    // Endpoint para contar las postulaciones de un trabajo
    @GetMapping("/trabajo/{trabajoId}/count")
    public ResponseEntity<Long> contarPostulacionesPorTrabajo(@PathVariable Integer trabajoId) {
        long count = postulacionService.countByTrabajoId(trabajoId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/trabajo/{trabajoId}/postulantes")
    public ResponseEntity<List<CandidatoDto>> listarPostulantesPorTrabajo(@PathVariable Integer trabajoId) {
        List<CandidatoDto> candidatos = postulacionService.listarPostulantesPorTrabajoId(trabajoId);
        return candidatos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(candidatos);
    }
}
