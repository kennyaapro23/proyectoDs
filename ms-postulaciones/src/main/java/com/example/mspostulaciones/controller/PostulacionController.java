package com.example.mspostulaciones.controller;


import com.example.mspostulaciones.entity.Postulacion;
import com.example.mspostulaciones.service.PostulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/postulacion")
public class PostulacionController {

    @Autowired
    private PostulacionService postulacionService;

    @GetMapping
    public ResponseEntity<List<Postulacion>> listar() {
        return ResponseEntity.ok(postulacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Postulacion>> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(postulacionService.listarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Postulacion> crear(@RequestBody Postulacion postulacion) {
        return ResponseEntity.ok(postulacionService.guardar(postulacion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Postulacion> actualizar(@PathVariable Integer id, @RequestBody Postulacion postulacion) {
        postulacion.setId(id);
        return ResponseEntity.ok(postulacionService.actualizar(postulacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        postulacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Postulacion> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam(value = "estado") String estado,
            @RequestParam(value = "comentario", required = false) String comentario
    ) {
        // Validar que el estado no sea nulo o vacío
        if (estado == null || estado.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(postulacionService.actualizarEstado(id, estado, comentario));
    }
}
