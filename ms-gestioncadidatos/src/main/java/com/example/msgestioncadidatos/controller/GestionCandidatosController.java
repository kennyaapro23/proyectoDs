package com.example.msgestioncadidatos.controller;

import com.example.msgestioncadidatos.entity.GestionCandidatos;

import com.example.msgestioncadidatos.service.GestionCandidatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gestioncandidatos")
public class GestionCandidatosController {

    @Autowired
    private GestionCandidatosService gestionCandidatosService;

    // Listar todos los candidatos
    @GetMapping
    public ResponseEntity<List<GestionCandidatos>> list() {
        List<GestionCandidatos> candidatos = gestionCandidatosService.list();
        if (candidatos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(candidatos);
    }

    // Obtener candidato por ID
    @GetMapping("/{id}")
    public ResponseEntity<GestionCandidatos> findById(@PathVariable Integer id) {
        Optional<GestionCandidatos> candidato = gestionCandidatosService.getById(id);
        if (candidato.isPresent()) {
            return ResponseEntity.ok(candidato.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Crear un nuevo candidato
    @PostMapping
    public ResponseEntity<GestionCandidatos> save(@RequestBody GestionCandidatos gestionCandidatos) {
        try {
            GestionCandidatos nuevoCandidato = gestionCandidatosService.save(gestionCandidatos);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCandidato);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Actualizar un candidato existente
    @PutMapping("/{id}")
    public ResponseEntity<GestionCandidatos> update(
            @PathVariable Integer id,
            @RequestBody GestionCandidatos gestionCandidatos) {
        if (!gestionCandidatosService.getById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        gestionCandidatos.setId(id);
        GestionCandidatos actualizado = gestionCandidatosService.update(gestionCandidatos);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar un candidato por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (!gestionCandidatosService.getById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Candidato no encontrado");
        }
        gestionCandidatosService.delete(id);
        return ResponseEntity.ok("Eliminación correcta");
    }
}
